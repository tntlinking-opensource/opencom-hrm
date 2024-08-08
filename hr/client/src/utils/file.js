/*获取文件扩展名*/
export function getExtensionName(filename) {
  const arrs = filename.split('.')
  if(arrs.length > 1) {
    return arrs.pop()
  }
  return null
}
