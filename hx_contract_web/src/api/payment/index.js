import request from '@/utils/request'
export function listPayment(query) {
  return request({
    url: '/payment/list',
    method: 'get',
    params: query
  })
}
export function getPayment(data) {
  return request({
    url: '/payment/detail',
    method: 'post',
    data: data
  })
}
export function addClaim(data) {
  return request({
    url: '/payment/addClaim',
    method: 'post',
    data: data
  })
}
export function cancelClaim(data) {
  return request({
    url: '/payment//claim/cancel',
    method: 'post',
    data: data
  })
}

export function listPaymentClaim(query) {
  return request({
    url: '/payment/claim/list',
    method: 'get',
    params: query
  })
}
export function listAllClaim(query) {
  return request({
    url: '/payment/claim/all/list',
    method: 'get',
    params: query
  })
}
export function listClaimDetail(query) {
  return request({
    url: '/payment/claim/detail',
    method: 'post',
    data: query
  })
}



