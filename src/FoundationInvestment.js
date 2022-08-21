/* eslint-disable react/prop-types */
import * as React from 'react';
import './App.css';

export default function FoundationInvestment({ data }) {
  const styleRows = data !== null && data !== undefined ? data.map((row, index) => (
    // eslint-disable-next-line react/jsx-filename-extension, react/no-array-index-key
    <tr key={index}>
      <td>{row.date}</td>
      <td>{row.code}</td>
      <td className="w250">{row.name}</td>
      <td>{row.initAmount}</td>
      <td>{row.initProfit}</td>
      <td>{row.dailyInvestAmount}</td>
      <td>{row.actualGain}</td>
      <td>{row.commission}</td>
      <td>{row.totalAmount}</td>
      <td className={row.totalProfit > 0 ? 'green' : 'red'}>{row.totalProfit}</td>
      <td>{row.profitRate}</td>
      <td>{row.isEnabled ? '正常' : '暂停'}</td>
    </tr>
  )) : null;
  return (
    <table>
      <thead style={{
        background: 'black',
      }}
      >
        <tr>
          <td>日期</td>
          <td>基金编号</td>
          <td className="w250">基金名称</td>
          <td>初始总额</td>
          <td>初始利润</td>
          <td>每日定投额度</td>
          <td>日涨幅</td>
          <td>买入费率</td>
          <td>总额</td>
          <td>总利润</td>
          <td>收益率</td>
          <td>是否执行</td>
        </tr>
      </thead>
      <tbody>
        {styleRows}
      </tbody>
    </table>
  );
}
