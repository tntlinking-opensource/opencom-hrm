/*测试环境配置*/
'use strict'
module.exports = {
  NODE_ENV: '"test"',
  BASE_API: '"http://61.172.179.26:8099/hr-back-test"',
  WEB_SOCKET_URL:'"wss://61.172.179.26:8099/hr-back-test/websocket/"',

  // 文件在线预览地址,配置应用服务器的端口地址
  FILE_PREVIEW_URL: '"http://61.172.179.26:8099/hr-back-test/"',
  // kkFile在线预览地址,配置文件服务的地址
  KK_FILE_URL: '"http://61.172.179.56:9898/insideOnlinePreview?url="',
}

/*算力平台配置*/
/*
'use strict'
module.exports = {
  NODE_ENV: '"test"',
  BASE_API: '"/hr-back-test"',
  WEB_SOCKET_URL:'"/hr-back-test/websocket/"',
}*/
