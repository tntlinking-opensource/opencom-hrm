
/**
* 实现千分位过滤器
* decimal 保留小数位数  默认为两位小数
**/
export function thousandsFilter(value, decimal) {
  if (typeof (value) === 'number') {
    if (typeof (decimal) != 'number') {
      decimal = 2
    }
    return (value).toFixed(decimal).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')
  }
}
/**
 *  格式化日期格式如：2017-9-19 18:04:33
 */
export function formatDate(value, type) {
  // 计算日期相关值
  let time = typeof value == 'number' ? new Date(value) : value;
  let Y = time.getFullYear();
  let M = time.getMonth() + 1;
  let D = time.getDate();
  let h = time.getHours();
  let m = time.getMinutes();
  let s = time.getSeconds();
  let week = time.getDay();
  // 如果传递了type的话
  if (type == undefined) {
    return Y + '-' + (M < 10 ? '0' + M : M) + '-' + (D < 10 ? '0' + D : D) + ' ' + (h < 10 ? '0' + h : h) + ':' + (m < 10 ? '0' + m : m) + ':' + (s < 10 ? '0' + s : s);
  } else if (type == 'week') {
    return week;
  }
  // 年月日时分：2022-10-13 18:04
  else if (type === 'datetime') {
    return `${Y}-${M < 10 ? '0' + M : M}-${D < 10 ? '0' + D : D} ${h < 10 ? '0' + h : h}:${m < 10 ? '0' + m : m}`
  }
}

export function sumFormat(value){
	if(!value) return '0.00';	
	var intPart =  Number(value)|0; //获取整数部分
	var intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'); //将整数部分每三位加一个逗号
	var floatPart = ".00"; //预定义小数部分
	if(typeof value !== 'string'){
	   value = value.toString();
	}
	var value2Array = value.split(".");
 
	//=2表示数据有小数位
	if(value2Array.length == 2) {
		floatPart = value2Array[1].toString(); //拿到小数部分
		if(floatPart.length > 2 ) { //若小数位数在两位以上，则四舍五入保留两位小数
			return parseFloat(value).toFixed(2);
		} else if(floatPart.length == 1) { //若小数位数只有一位，补0
			return intPartFormat + "." + floatPart + '0';
		} else {
			return intPartFormat + "." + floatPart;
		}
 
	} else {  //数据为整数
		return intPartFormat + floatPart;
	}
}
