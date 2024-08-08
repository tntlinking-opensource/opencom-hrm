'use strict'
module.exports = {
  NODE_ENV: '"production"',
  BASE_API: '"http://10.1.1.116:9999"',

    // 企业微信授权登录
    REQUIRE_AUTH: true,
    //企业微信项目包路径
    CONTEXT_PATH: '"/lease-epwechat-test"',
  
  // 报表地址配置
  REPORT_CLIENT_URL: '"http://59.80.34.149/dataease/frontend/"',
  REPORT_SERVER_URL: '"http://59.80.34.149/dataease/backend/"',

  VUE_APP_API_OAUTH2_ENABLE: 'true',
  VUE_APP_API_OAUTH2_GRANTTYPE: '"password, client_credentials"', // 客户端授权码模式（授权码：authorization_code；密码模式：password；客户端模式：client_credentials）
  
}
