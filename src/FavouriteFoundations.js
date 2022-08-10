import * as React from 'react';

// eslint-disable-next-line react/prop-types
export default function FavouriteFoundations({ data }) {
  // eslint-disable-next-line react/prop-types
  const styleRows = data !== null && data !== undefined ? data.map((row) => (
    // eslint-disable-next-line react/jsx-filename-extension
    <tr key={row.code}>
      <td className="w70"><a target="blank" href={`http://fund.eastmoney.com/${row.code}.html`}>{row.code}</a></td>
      <td className="w170">{row.name}</td>
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
        </tr>
      </thead>
      <tbody>
        {styleRows}
      </tbody>
    </table>
  );
}
