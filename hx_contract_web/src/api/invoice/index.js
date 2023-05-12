import request from '@/utils/request'
export function addInvoice(data) {
  return request({
    url: '/invoice/add',
    method: 'post',
    data: data
  })
}
export function updateInvoice(data) {
  return request({
    url: '/invoice/update',
    method: 'post',
    data: data
  })
}
export function addDelivery(data) {
  return request({
    url: '/order/consignment/add',
    method: 'post',
    data: data
  })
}

export function updateDelivery(data) {
  return request({
    url: '/order/consignment/update',
    method: 'post',
    data: data
  })
}

export function listInvoice(query) {
  return request({
    url: '/invoice/list',
    method: 'get',
    params: query
  })
}

export function getAddressByCode(query) {
  return request({
    url: '/customer/getAddressByCode',
    method: 'get',
    params: query
  })
}

/**
 * 获取发票详情
 * @param query
 * @returns {*}
 */
export function getInvoiceDetail(query) {
  return request({
    url: '/invoice/detail',
    method: 'get',
    params: query
  })
}

export function approveInvoice(data) {
  return request({
    url: '/invoice/approve',
    method: 'post',
    data: data
  })
}

