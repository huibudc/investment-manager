import database.FoundationDao;
import database.FoundationInvestmentDao;
import ratpack.core.http.MutableHeaders;
import ratpack.core.server.RatpackServer;
import scheduler.Job;
import service.CrawlerService;
import service.FoundationEnricher;
import service.FoundationInvestmentService;

import static utils.Utils.GSON;

public class App {
    private final FoundationDao foundationDao;
    private final FoundationInvestmentService foundationInvestmentService;

    public App(FoundationDao foundationDao, FoundationInvestmentService foundationInvestmentService) {
        this.foundationDao = foundationDao;
        this.foundationInvestmentService = foundationInvestmentService;
    }

    public static void main(String... args) throws Exception {
        FoundationDao dao = new FoundationDao();
        CrawlerService crawlerService = new CrawlerService();
        Job job = new Job(crawlerService, dao);
        FoundationInvestmentDao foundationInvestmentDao = new FoundationInvestmentDao();
        new App(dao, new FoundationInvestmentService(job, foundationInvestmentDao,dao)).start();
    }

    private void start() throws Exception {
        RatpackServer.start(serverSpec ->
                serverSpec
                        .serverConfig(builder -> {
                            builder.findBaseDir("web")
                                    .port(8080)
                                    .development(false);
                        })
                        .handlers(root ->
                                        root.all(ctx -> {
                                                    MutableHeaders headers = ctx.getResponse().getHeaders();
                                                    headers.add("Access-Control-Allow-Origin", "*");
                                                    ctx.next();
                                                }).
                                prefix("investment-management", chain -> {
                                    chain.files(fileHandlerSpec -> fileHandlerSpec.dir("web").indexFiles("index.html"));
                                    chain.prefix("foundation-data", foundation -> {
                                                foundation.get("", ctx -> ctx.getResponse().send(GSON.toJson(FoundationEnricher.rankingFoundations(foundationDao.foundations()))));
                                                foundation.get(":code", ctx -> {
                                                    String code = ctx.getPathTokens().get("code");
                                                    ctx.getResponse().send(GSON.toJson(foundationDao.foundation(code)));
                                                });
                                            })
                                            .prefix("invested-foundation", foundation -> {
                                                foundation.get("", ctx -> ctx.getResponse().send(GSON.toJson(foundationDao.investFoundations())));
                                                foundation.get(":code", ctx -> {
                                                    String code = ctx.getPathTokens().get("code");
                                                    ctx.getResponse().send(GSON.toJson(foundationDao.investFoundation(code)));
                                                });
                                            })
                                            .prefix("foundation-investment", foundation -> {
                                                foundation.get("", ctx -> ctx.getResponse().send(GSON.toJson(foundationInvestmentService.foundationInvestments())));
                                            })
                                            .get("foundation-list", ctx -> {
                                                ctx.getResponse().send(GSON.toJson(foundationDao.allMarketFoundations()));
                                            })
                                    ;
                                })
                        ));
    }
}
