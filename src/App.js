/* eslint-disable max-len */
/* eslint-disable react/jsx-filename-extension */
import * as React from 'react';
import './App.css';
import * as _ from 'lodash';
import FoundationData from './FoundationData';
import FoundationInvestment from './FoundationInvestment';
import Filter from './Filter';
import FavouriteFoundations from './FavouriteFoundations';
import LineBarChart from './LineBarChart';

const axios = require('axios');

export default function App() {
  const [foundationData, setFoundationData] = React.useState([]);
  const [foundationInvestment, setFoundationInvestment] = React.useState([]);
  const [alertChecked, setAlertChecked] = React.useState(false);
  const [favouriteFoundations, setFavouriteFoundations] = React.useState([]);
  const [foundationFilter, setFoundationFilter] = React.useState('');
  const [shouldShowFavourite, setShouldShowFavourite] = React.useState(true);
  const [shouldShowInvestment, setShouldShowInvestment] = React.useState(true);

  React.useEffect(() => () => {
    axios.get('http://localhost:8080/investment-management/foundation-data')
      .then((res) => {
        setFoundationData(res.data);
      });
    axios.get('http://localhost:8080/investment-management/foundation-investment')
      .then((res) => {
        setFoundationInvestment(res.data);
      });

    axios.get('http://localhost:8080/investment-management/favourite-foundations')
      .then((res) => {
        setFavouriteFoundations(res.data);
      });
  }, []);

  const foundationDataFilter = (filter) => {
    let displayData = foundationData.map((it) => it);
    if (filter !== null && filter !== undefined && filter.length > 0) {
      // eslint-disable-next-line max-len
      displayData = displayData.filter((it) => it.name.indexOf(filter) > -1 || it.code.indexOf(filter) > -1);
    }
    if (alertChecked) {
      displayData = displayData.filter((it) => it.shouldWarn);
    }
    return displayData;
  };

  const commenFilter = (filter, data) => {
    let displayData = data.map((it) => it);
    if (filter !== null && filter !== undefined && filter.length > 0) {
      // eslint-disable-next-line max-len
      displayData = displayData.filter((it) => it.name.indexOf(filter) > -1 || it.code.indexOf(filter) > -1);
    }
    return displayData;
  };

  const onSelect = (e) => {
    setFoundationFilter(e.target.value.split('-')[0]);
  };

  const selectOptionFn = () => {
    if (foundationData === null || selectOptionFn === undefined) {
      return [];
    }
    const selectOptions = [(<option key="defualt" value="" selected>please select</option>)];
    const uniquMapping = _.uniq(foundationData.map((it) => `${it.code}-${it.name}`)).sort((a, b) => a.localeCompare(b));
    uniquMapping.map((it) => (<option key={it} value={it}>{`${it}`}</option>)).forEach((it) => selectOptions.push(it));
    return (
      <select
        className="form-select"
        onChange={onSelect}
      >
        {selectOptions}
      </select>
    );
  };

  return (
    <div>
      <div style={{ position: 'fixed' }}>
        <span>
          <b>坚持, 不哭</b>
          &#129315;&#129315;
        </span>
        {selectOptionFn()}
      </div>
      <div>,</div>
      <div>
        <div style={{ display: 'flex' }}>
          <p>投资基金</p>
          <button type="button" className="collapse-button" onClick={() => setShouldShowInvestment(!shouldShowInvestment)}>{shouldShowInvestment ? '收起!' : '展开!'}</button>
        </div>
        <div>
          <LineBarChart data={foundationInvestment} />
        </div>
        { shouldShowInvestment
          ? <FoundationInvestment data={commenFilter(foundationFilter, foundationInvestment)} />
          : null}

      </div>
      <div>
        <div style={{ display: 'flex', marginTop: '20px' }}>
          <p>关注基金</p>
          <button type="button" className="collapse-button" onClick={() => setShouldShowFavourite(!shouldShowFavourite)}>{shouldShowFavourite ? '收起!' : '展开!'}</button>
        </div>
        {shouldShowFavourite
          ? <FavouriteFoundations data={commenFilter(foundationFilter, favouriteFoundations)} />
          : null}
      </div>
      <div style={{
        marginTop: '20px',
      }}
      >
        <Filter
          checkedAllert={alertChecked}
          alertOnChecked={() => { setAlertChecked(!alertChecked); }}
        />
        <FoundationData
          data={foundationDataFilter(foundationFilter)}
        />
      </div>
      <div style={{ marginTop: '50px' }} />
    </div>
  );
}
