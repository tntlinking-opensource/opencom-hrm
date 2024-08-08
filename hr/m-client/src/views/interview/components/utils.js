
//将标准时间改成yyyymmdd格式
export function changeFormatter(val) {
  let y = val.getFullYear();
  let m = val.getMonth() + 1;
  m = m < 10 ? "0" + m : m;
  let d = val.getDate();
  d = d < 10 ? "0" + d : d;
  let str = `${y}-${m}-${d}`;
  return str;
}
//将yyyymmdd格式修改成标准格式
export function changeUTC(val) {
  let arr = val.split("-");
  let y = Number(arr[0]);
  let m = Number(arr[1]) - 1;
  let d = Number(arr[2]);
  if (!y || (!m && m !== 0) || !d) {
    //如果存在不合规范的，返回null
    return null;
  } else {
    return new Date(y, m, d);
  }
}