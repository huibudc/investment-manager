import * as React from 'react';

// eslint-disable-next-line react/prop-types
export default function FoundationData({ data }) {
  const func = (val) => val.replace('|', '/');
  // eslint-disable-next-line react/prop-types
  const styleRows = data !== null && data !== undefined ? data.map((row, index) => (
    // eslint-disable-next-line react/jsx-filename-extension
    <tr
      // eslint-disable-next-line react/no-array-index-key
      key={index}
      style={{
        background: row.shouldWarn ? 'red' : '#5ee25e',
      }}
    >
      <td className="w70"><a target="blank" href={`http://fund.eastmoney.com/${row.code}.html`}>{row.code}</a></td>
      <td className="w170">{row.name}</td>
      <td className="w100">{row.type}</td>
      <td className="w70">{row.date}</td>
      <td className="w70">{row.estimatedValue}</td>
      <td className="w70">{row.estimatedGain}</td>
      <td>{row.actualValue}</td>
      <td>{row.actualGain}</td>
      <td className="w70">{row.accumulativeValue}</td>
      <td className="w70">{row.gainWithinWeek}</td>
      <td className="w70">{row.gainWithinMonth}</td>
      <td className="w70">{row.gainWithinThreeMonth}</td>
      <td className="w70">{func(row.gainWithinSixMonth)}</td>
      <td>{func(row.rankWithinWeek)}</td>
      <td>{func(row.rankWithinMonth)}</td>
      <td>{func(row.rankWithinThreeMonth)}</td>
      <td>{func(row.rankWithinSixMonth)}</td>
      <td className="w70">{row.shouldWarn ? '警告' : '正常'}</td>
    </tr>
  )) : null;
  return (
    <table>
      <thead style={{
        background: 'black',
      }}
      >
        <tr>
          <td className="w70">基金编号</td>
          <td className="w170">基金名称</td>
          <td className="w100">基金类型</td>
          <td className="w70">日期</td>
          <td className="w70">估值</td>
          <td className="w70">估值涨幅</td>
          <td>净值</td>
          <td>真实涨幅</td>
          <td className="w70">累计净值</td>
          <td className="w70">周涨幅</td>
          <td className="w70">月涨幅</td>
          <td className="w70">三月涨幅</td>
          <td className="w70">半年涨幅</td>
          <td>周排名</td>
          <td>月排名</td>
          <td>三月排名</td>
          <td>半年排名</td>
          <td className="w70">警告</td>
        </tr>
      </thead>
      <tbody>
        {styleRows}
      </tbody>
    </table>
  );
}
