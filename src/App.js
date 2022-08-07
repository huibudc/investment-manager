/* eslint-disable react/jsx-filename-extension */
import * as React from 'react';
import './App.css';
import FoundationData from './FoundationData';
import FoundationInvestment from './FoundationInvestment';
import Filter from './Filter';

const axios = require('axios');

export default function App() {
  const [foundationData, setFoundationData] = React.useState([]);
  const [foundationInvestment, setFoundationInvestment] = React.useState([]);
  const [alertChecked, setAlertChecked] = React.useState(false);
  const [foundationDatafilterValue, setFoundationDatafilterValue] = React.useState('');

  React.useEffect(() => () => {
    axios.get('http://localhost:8080/investment-management/foundation-data')
      .then((res) => {
        setFoundationData(res.data);
      });
    axios.get('http://localhost:8080/investment-management/foundation-investment')
      .then((res) => {
        setFoundationInvestment(res.data);
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
        <h1>投资基金</h1>
        <FoundationInvestment data={foundationInvestment} />
      </div>
      <div style={{
        'margin-top': '20px',
      }}
      >
        <h1>基金数据</h1>
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
    </div>
  );
}
