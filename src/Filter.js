/* eslint-disable jsx-a11y/label-has-associated-control */
import * as React from 'react';

// eslint-disable-next-line react/prop-types
export default function Filter({ alertChecked, alertOnChecked }) {
  return (
    // eslint-disable-next-line react/jsx-filename-extension
    <div>
      <label>
        <span>警告:</span>
        <input type="checkbox" onChange={alertOnChecked} checked={alertChecked} key={alertChecked} />
      </label>
    </div>
  );
}
