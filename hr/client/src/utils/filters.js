
/**
* 实现千分位过滤器
* decimal 保留小数位数  默认为两位小数
**/
export function thousandsFilter(value, decimal) {
  if (typeof (value) === 'number') {
  	if(typeof (decimal) != 'number') {
  		decimal = 2
  	}
  	return (value).toFixed(decimal).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')
  }
}

