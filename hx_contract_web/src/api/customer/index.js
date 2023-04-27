import request from '@/utils/request'
import {parseStrEmpty} from "@/utils/ruoyi";

export function listCustomer(query) {
  return request({
    url: '/customer/list',
    method: 'get',
    params: query
  })
}

export function getCustomer(cid) {
  return request({
    url: '/customer/detail/' + parseStrEmpty(cid),
    method: 'get'
  })
}

export function addAddress(data) {
  return request({
    url: '/customer/addAddress',
    method: 'post',
    data: data
  })
}

export function getAddress(data) {
  return request({
    url: '/customer/getAddress',
    method: 'post',
    data: data
  })
}

export function updateAddress(data) {
  return request({
    url: '/customer/updateAddress',
    method: 'put',
    data: data
  })
}

export function delAddress(aid) {
  return request({
    url: '/customer/deleteAddress/' + aid,
    method: 'delete'
  })
}

export function addOpenBank(data) {
  return request({
    url: '/customer/addOpenBank',
    method: 'post',
    data: data
  })
}

export function updateOpenBank(data) {
  return request({
    url: '/customer/updateOpenBank',
    method: 'put',
    data: data
  })
}

export function getOpenBank(data) {
  return request({
    url: '/customer/getOpenBank',
    method: 'post',
    data: data
  })
}
export function getOpenBankByBe(data) {
  return request({
    url: '/customer/getOpenBankByBe',
    method: 'post',
    data: data
  })
}



export function delOpeningBank(data) {
  return request({
    url: '/customer/removeOpenBank/',
    method: 'delete',
    data: data
  })
}
