/**
 * 验证是否为外部链接
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 验证是否为URL
 */
export function validURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * 邮箱验证
 */
export function validEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * 验证IP地址
 */
export function validIp(ip) {
  const reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
  return reg.test(ip)
}

/**
 * 验证是否为正整数
 */
export function validInt(value) {
  const reg = /^\+?[1-9][0-9]*$/;
  return reg.test(value);
}

/**
 * 字符串验证
 */
export function isString(str) {
  return typeof str === 'string' || str instanceof String;

}

/**
 * 验证是否为数组
 */
export function isArray(arg) {
  if(typeof Array.isArray === 'undefined') {
    return Object.prototype.toString.call(arg) === '[object Array]'
  }
  return Array.isArray(arg)
}

/**
 * 验证是否为对象
 */
export function isObject(obj) {
  return (typeof obj === 'object' || Object.prototype.toString.call(obj) === '[object Object]')
}

/**
 * 验证是否为函数
 */
export function isFunction(func) {
  return (typeof func === 'function' || Object.prototype.toString.call(func) === '[object Function]')
}

/**
 * 数据非空验证，验证数据是否为 null undefined [] {} ''
 */
export function isEmpty(data) {
  if(data === null) return true;
  if(data === undefined) return true;
  if(Object.prototype.toString.call(data) === '[object Array]') return data.length === 0;
  if(Object.prototype.toString.call(data) === '[object Object]') return Object.keys(data).length === 0;
  if(typeof data === 'string') return data.trim() === '';
  return false
}

