const tokenKey = 'token'
const userKey = 'user'
const weChatUserKey = 'weChatUser'
const weChatRouter = 'weChatRouter'

export function getToken(key) {
	let data = localStorage.getItem(key)
	if (data) {
	 // console.log(JSON.parse(data))
		return JSON.parse(data)
	}
}

export function setToken(key, token) {
	localStorage.setItem(key, JSON.stringify(token))
}

export function removeToken(key) {
	return localStorage.removeItem(key)
}


export function getSessionToken(key) {
	let data = sessionStorage.getItem(key)
	if (data) {
		return JSON.parse(data)
	}
}

export function setSessionToken(key, token) {
	sessionStorage.setItem(key, JSON.stringify(token))
}

export function removeSessionToken(key) {
	return sessionStorage.removeItem(key)
}


export function getWeChatUser(key) {
  let data = localStorage.getItem(key)
  if (data) {
    return JSON.parse(data)
  }
}

export function setWeChatUser(key, token) {
  localStorage.setItem(key, JSON.stringify(token))
}

export function removeWeChatUser(key) {
  return localStorage.removeItem(key)
}

// ---

export function setLocalToken(token) {
	sessionStorage.setItem(tokenKey, JSON.stringify(token))
}

export function getLocalToken() {
	return JSON.parse(sessionStorage.getItem(tokenKey))
}

export function removeLocalToken() {
	return sessionStorage.removeItem(tokenKey)
}

export function setLocalUser(user) {
	sessionStorage.setItem(userKey, JSON.stringify(user))
}

export function getLocalUser() {
	return JSON.parse(sessionStorage.getItem(userKey))
}

export function removeLocalUser() {
	return sessionStorage.removeItem(userKey)
}

export function setLocalWeChatUser(weChatUser) {
	sessionStorage.setItem(weChatUserKey, JSON.stringify(weChatUser))
}

export function getLocalWeChatUser() {
	return JSON.parse(sessionStorage.getItem(weChatUserKey))
}

export function removeLocalWeChatUser() {
	return sessionStorage.removeItem(weChatUserKey)
}

export function setLocalRouters(routers) {
	sessionStorage.setItem(weChatRouter, JSON.stringify(routers))
}

export function getLocalRouters() {
	return JSON.parse(sessionStorage.getItem(weChatRouter))
}

export function removeRouters() {
	return sessionStorage.removeItem(weChatRouter)
}