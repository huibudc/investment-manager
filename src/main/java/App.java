import database.FavouriteFoundationDao;
import database.FoundationDao;
import database.FoundationInvestmentDao;
import ratpack.core.http.MutableHeaders;
import ratpack.core.http.internal.HttpHeaderConstants;
import ratpack.core.server.RatpackServer;
import scheduler.Job;
import service.CrawlerService;
import service.FoundationEnricher;
import service.FoundationInvestmentService;

import static utils.Utils.GSON;

public class App {
    private final FoundationDao foundationDao;
    private final FavouriteFoundationDao favouriteFoundationDao;
    private final FoundationEnricher foundationEnricher;
    private final FoundationInvestmentService foundationInvestmentService;

    public App(FoundationDao foundationDao, FavouriteFoundationDao favouriteFoundationDao, FoundationEnricher foundationEnricher, FoundationInvestmentService foundationInvestmentService) {
        this.foundationDao = foundationDao;
        this.favouriteFoundationDao = favouriteFoundationDao;
        this.foundationEnricher = foundationEnricher;
        this.foundationInvestmentService = foundationInvestmentService;
    }

    public static void main(String... args) throws Exception {
        FoundationDao dao = new FoundationDao();
        FoundationEnricher foundationEnricher = new FoundationEnricher(dao);
        CrawlerService crawlerService = new CrawlerService();
        Job job = new Job(crawlerService, dao);
        FoundationInvestmentDao foundationInvestmentDao = new FoundationInvestmentDao();
        new App(dao, new FavouriteFoundationDao(), foundationEnricher, new FoundationInvestmentService(job, foundationInvestmentDao, dao)).start();
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
                                                        foundation.get("", ctx -> ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(foundationEnricher.rankingFoundations(foundationDao.foundations()))));
                                                        foundation.get(":code", ctx -> {
                                                            String code = ctx.getPathTokens().get("code");
                                                            ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(foundationDao.foundation(code)));
                                                        });
                                                    })
                                                    .prefix("invested-foundation", foundation -> {
                                                        foundation.get("", ctx -> ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(foundationDao.investFoundations())));
                                                        foundation.get(":code", ctx -> {
                                                            String code = ctx.getPathTokens().get("code");
                                                            ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(foundationDao.investFoundation(code)));
                                                        });
                                                    })
                                                    .prefix("foundation-investment", foundation -> {
                                                        foundation.get("", ctx -> ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(foundationInvestmentService.foundationInvestments())));
                                                    })
                                                    .get("foundation-list", ctx -> {
                                                        ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(foundationDao.allMarketFoundations()));
                                                    })
                                                    .get("favourite-foundations", ctx -> {
                                                        ctx.getResponse().send(HttpHeaderConstants.JSON, GSON.toJson(favouriteFoundationDao.favouriteFoundations()));
                                                    })
                                            ;
                                        })
                        ));
    }
}
