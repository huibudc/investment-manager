/* eslint-disable max-len */
/* eslint-disable react/prop-types */
import React from 'react';
import * as echarts from 'echarts';
import * as _ from 'lodash';

// eslint-disable-next-line react/prop-types
export default function LineBarChart({ data }) {
  const chartRef = React.useRef(null);
  const sortedData = data !== null && data !== undefined ? data.sort((a, b) => a.date.localeCompare(b.date)) : [];
  const names = sortedData !== null && sortedData !== undefined ? _.uniq(sortedData.map((it) => it.name)) : [];
  const dates = sortedData !== null && sortedData !== undefined ? _.uniq(sortedData.map((it) => it.date)) : [];
  const series = sortedData !== null && sortedData !== undefined ? names.map((it) => ({
    name: it,
    // eslint-disable-next-line max-len
    data: data.filter((foundationData) => foundationData.name === it).map((foundationData) => foundationData.totalProfit),
    type: 'line',
  })) : [];
  React.useEffect(() => {
    const chartInstance = echarts.init(chartRef.current);
    const option = {
      title: {
        text: '盈利',
      },
      tooltip: {
        trigger: 'axis',
      },
      legend: {
        data: names,
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true,
      },
      toolbox: {
        feature: {
          saveAsImage: {},
        },
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: dates,
      },
      yAxis: {
        type: 'value',
      },
      series,
    };
    chartInstance.setOption(option);
  });

  return (
    <div>
      <div ref={chartRef} style={{ height: '400px' }} />
    </div>
  );
}
