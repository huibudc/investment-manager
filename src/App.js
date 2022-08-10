/* eslint-disable react/jsx-filename-extension */
import * as React from 'react';
import './App.css';
import FoundationData from './FoundationData';
import FoundationInvestment from './FoundationInvestment';
import Filter from './Filter';
import FavouriteFoundations from './FavouriteFoundations';

const axios = require('axios');

export default function App() {
  const [foundationData, setFoundationData] = React.useState([]);
  const [foundationInvestment, setFoundationInvestment] = React.useState([]);
  const [alertChecked, setAlertChecked] = React.useState(false);
  const [foundationDatafilterValue, setFoundationDatafilterValue] = React.useState('');
  const [favouriteFoundations, setFavouriteFoundations] = React.useState([]);

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

  return (
    <div>
      <div>
        <h1>关注基金</h1>
        <button type="button" className="collapse-button">收起!</button>
        <FavouriteFoundations data={favouriteFoundations} />
      </div>
      <div>
        <div style={{ display: 'flex' }}>
          <p>投资基金</p>
          <button type="button" className="collapse-button">收起!</button>
        </div>
        <FoundationInvestment data={foundationInvestment} />
      </div>
      <div style={{
        marginTop: '20px',
      }}
      >
        <Filter
          filterValue={foundationDatafilterValue}
          onChange={(e) => { setFoundationDatafilterValue(e.target.value); }}
          checkedAllert={alertChecked}
          alertOnChecked={() => { setAlertChecked(!alertChecked); }}
        />
        <FoundationData
          data={foundationDataFilter(foundationDatafilterValue)}
        />
      </div>
      <div style={{ marginTop: '50px' }} />
    </div>
  );
}
