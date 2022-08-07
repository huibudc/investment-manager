/* eslint-disable jsx-a11y/label-has-associated-control */
import * as React from 'react';

export default function Filter({
  // eslint-disable-next-line react/prop-types
  filterValue, onChange, alertChecked, alertOnChecked,
}) {
  return (
    // eslint-disable-next-line react/jsx-filename-extension
    <div>
      <babel>
        <span>代码或名称:</span>
        <input
          style={{
            margin: '5px',
          }}
          onChange={onChange}
          value={filterValue}
          placeholder="请输入代码或名称"
        />
      </babel>

      <label>
        <span>警告:</span>
        <input type="checkbox" onChange={alertOnChecked} checked={alertChecked} key={alertChecked} />
      </label>
    </div>
  );
}
