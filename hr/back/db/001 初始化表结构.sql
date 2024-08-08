SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

CREATE TABLE dc_column_view
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 业务表
	dc_table_id bigint(21) unsigned NOT NULL COMMENT '业务表',
	-- 用户
	user_id bigint(21) unsigned COMMENT '用户',
	-- 路由
	router_id bigint(21) unsigned COMMENT '路由',
	-- 列参数
	json longtext COMMENT '列参数',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


CREATE TABLE dc_condition
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 用户
	user_id bigint(21) unsigned COMMENT '用户',
	-- 路由
	router_id bigint(21) unsigned COMMENT '路由',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 排序
	sort bigint(21) unsigned NOT NULL COMMENT '排序',
	-- 条件
	conditions text COMMENT '条件',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


CREATE TABLE dc_table
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 表来源
	source varchar(128) COMMENT '表来源',
	-- 代码方案
	scheme_id bigint(21) NOT NULL COMMENT '代码方案',
	-- 实体类名称
	class_name varchar(128) COMMENT '实体类名称',
	comments varchar(64),
	-- 关联父表外键
	parent_table_fk varchar(64) COMMENT '关联父表外键',
	-- 0：否，1：是
	is_main_table varchar(1) COMMENT '0：否，1：是',
	-- 0：否，1：是
	is_association varchar(1) COMMENT '0：否，1：是',
	-- 关联对象字段
	association_field varchar(64) COMMENT '关联对象字段',
	-- 是否检查版本
	check_version varchar(1) DEFAULT '0' NOT NULL COMMENT '是否检查版本',
	-- （0：否，1：是）
	is_one_to_one varchar(1) DEFAULT '0' COMMENT '（0：否，1：是）',
	-- 筛选条件
	filter varchar(2048) COMMENT '筛选条件',
	-- 表约束
	constraint_json text COMMENT '表约束',
	-- 排序字段
	order_columns varchar(2048) COMMENT '排序字段',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


-- 公司应用版本
CREATE TABLE gen_company_app
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 公司
	company_id bigint(21) unsigned NOT NULL COMMENT '公司',
	-- 租户应用版本
	tenant_app_version_id bigint(21) unsigned NOT NULL COMMENT '租户应用版本',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' NOT NULL COMMENT '禁用',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '公司应用版本' DEFAULT CHARACTER SET utf8;


CREATE TABLE notice_receive_site
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 标识
	send_id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 消息接收者id
	receive_by bigint(21) unsigned NOT NULL COMMENT '消息接收者id',
	-- 已读
	read_status varchar(1) COMMENT '已读',
	-- 读取时间
	read_time varchar(24) COMMENT '读取时间',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE notice_record_email
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 消息接收者
	receiver bigint(20) unsigned NOT NULL COMMENT '消息接收者',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE notice_send
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 发送人id
	sender_id bigint(21) unsigned NOT NULL COMMENT '发送人id',
	-- 标题
	title varchar(50) NOT NULL COMMENT '标题',
	-- 是否发送站内
	is_site varchar(1) NOT NULL COMMENT '是否发送站内',
	-- 是否发送微信
	is_wechat varchar(1) NOT NULL COMMENT '是否发送微信',
	-- 是否发送邮件
	is_email varchar(1) NOT NULL COMMENT '是否发送邮件',
	-- 是否发送短信
	is_sms varchar(1) NOT NULL COMMENT '是否发送短信',
	-- 站内内容
	site_content longtext COMMENT '站内内容',
	-- 邮件内容
	email_content longtext COMMENT '邮件内容',
	-- 微信内容
	wechat_content varchar(500) COMMENT '微信内容',
	-- 短信内容
	sms_content varchar(500) COMMENT '短信内容',
	-- 接收人
	received_by longtext NOT NULL COMMENT '接收人',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE notice_send_timer
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE TABLE notice_unsubscribe
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 用户
	user_id bigint(21) unsigned NOT NULL COMMENT '用户',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


-- 公司
CREATE TABLE org_company
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 编号
	code varchar(64) NOT NULL COMMENT '编号',
	-- 全称
	full_name varchar(512) NOT NULL COMMENT '全称',
	-- 父级
	parent_id bigint(21) unsigned NOT NULL COMMENT '父级',
	-- 全标识
	ids varchar(2048) NOT NULL COMMENT '全标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 排序
	sort bigint(21) unsigned DEFAULT 0 NOT NULL COMMENT '排序',
	-- 董事长
	chairman bigint(21) unsigned COMMENT '董事长',
	-- 总经理
	manager bigint(21) unsigned COMMENT '总经理',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '公司' DEFAULT CHARACTER SET utf8;


-- 部门
CREATE TABLE org_department
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 公司
	company_id bigint(21) unsigned NOT NULL COMMENT '公司',
	-- 编号
	code varchar(64) NOT NULL COMMENT '编号',
	-- 父级
	parent_id bigint(21) unsigned NOT NULL COMMENT '父级',
	-- 全标识
	ids varchar(2048) NOT NULL COMMENT '全标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 排序
	sort bigint(21) unsigned DEFAULT 0 NOT NULL COMMENT '排序',
	-- 总监
	director bigint(21) unsigned COMMENT '总监',
	-- 经理
	manager bigint(21) unsigned COMMENT '经理',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '部门' DEFAULT CHARACTER SET utf8;


CREATE TABLE schedule_job
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 名称
	name varchar(128) COMMENT '名称',
	-- 编号
	code varchar(64) COMMENT '编号',
	-- 任务表达式
	cron varchar(128) COMMENT '任务表达式',
	-- 任务状态
	status varchar(64) COMMENT '任务状态',
	-- 是否禁用
	is_lock varchar(1) COMMENT '是否禁用',
	-- 定时任务类全路径
	java_class varchar(150) NOT NULL COMMENT '定时任务类全路径',
	-- 定时任务方法名称
	java_method varchar(30) NOT NULL COMMENT '定时任务方法名称',
	-- 定时任务方法入参
	params varchar(100) COMMENT '定时任务方法入参',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


-- 操作日志
CREATE TABLE sys_action
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 操作类型
	action_type varchar(128) NOT NULL COMMENT '操作类型',
	-- 对象类型
	object_type bigint(21) unsigned NOT NULL COMMENT '对象类型',
	-- 业务对象
	object_id bigint(21) unsigned NOT NULL COMMENT '业务对象',
	-- 对象名称
	object_name varchar(128) COMMENT '对象名称',
	-- 已还原
	status varchar(1) DEFAULT '0' NOT NULL COMMENT '已还原',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '操作日志' DEFAULT CHARACTER SET utf8;


-- 回收站
CREATE TABLE sys_action_recycle
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 日志
	action_id bigint(21) unsigned NOT NULL COMMENT '日志',
	-- 表名
	table_name varchar(128) NOT NULL COMMENT '表名',
	-- 业务对象
	object_id bigint(21) unsigned NOT NULL COMMENT '业务对象',
	-- 对象名称
	object_name varchar(128) COMMENT '对象名称',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '回收站' DEFAULT CHARACTER SET utf8;


-- 功能收藏夹
CREATE TABLE sys_collect
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 用户
	user_id bigint(21) unsigned COMMENT '用户',
	-- 路由
	router_id bigint(21) unsigned NOT NULL COMMENT '路由',
	-- 排序
	sort bigint(21) unsigned DEFAULT 0 NOT NULL COMMENT '排序',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '功能收藏夹' DEFAULT CHARACTER SET utf8;


-- 数据权限
CREATE TABLE sys_data_permission
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 路由
	router_id bigint(21) unsigned NOT NULL COMMENT '路由',
	-- 角色
	role_id bigint(21) unsigned NOT NULL COMMENT '角色',
	-- 元数据
	meta_id bigint(21) unsigned NOT NULL COMMENT '元数据',
	-- 权限条件
	conditions text COMMENT '权限条件',
	PRIMARY KEY (id)
) COMMENT = '数据权限' DEFAULT CHARACTER SET utf8;


-- 字典项
CREATE TABLE sys_dict_item
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 字典类
	dict_type_id bigint(21) unsigned NOT NULL COMMENT '字典类',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 字典项值
	value varchar(128) NOT NULL COMMENT '字典项值',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '字典项' DEFAULT CHARACTER SET utf8;


-- 字典类型
CREATE TABLE sys_dict_type
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 编号
	code varchar(64) NOT NULL COMMENT '编号',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 是否系统级
	is_system varchar(1) DEFAULT '0' NOT NULL COMMENT '是否系统级',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '字典类型' DEFAULT CHARACTER SET utf8;


-- 附件表
CREATE TABLE sys_file
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 业务表
	object_id bigint(21) unsigned NOT NULL COMMENT '业务表',
	-- 名称
	name varchar(128) COMMENT '名称',
	-- 文件类型
	file_type varchar(64) COMMENT '文件类型',
	-- 文件大小
	file_size bigint(21) unsigned COMMENT '文件大小',
	-- 文件路径
	file_url varchar(255) COMMENT '文件路径',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '附件表' DEFAULT CHARACTER SET utf8;


-- 附件存储
CREATE TABLE sys_file_content
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 图片字节
	byte longblob COMMENT '图片字节',
	-- 文件类型
	file_type varchar(64) COMMENT '文件类型',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '附件存储' DEFAULT CHARACTER SET utf8;


-- 自定义主题
CREATE TABLE sys_personal_theme
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 用户
	user_id bigint(21) unsigned NOT NULL COMMENT '用户',
	-- 主题
	theme text NOT NULL COMMENT '主题',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id),
	UNIQUE (user_id)
) COMMENT = '自定义主题' DEFAULT CHARACTER SET utf8;


-- 资源
CREATE TABLE sys_resource
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 编号
	code varchar(64) NOT NULL COMMENT '编号',
	-- URL
	url varchar(128) NOT NULL COMMENT 'URL',
	-- 权限
	permission varchar(32) COMMENT '权限',
	-- 可以授权
	can_permission varchar(1) DEFAULT '1' NOT NULL COMMENT '可以授权',
	-- 默认资源
	is_default varchar(1) DEFAULT '0' NOT NULL COMMENT '默认资源',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' COMMENT '禁用',
	-- 路由
	router_id bigint(21) unsigned NOT NULL COMMENT '路由',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	-- 扩展属性
	properties varchar(1024) COMMENT '扩展属性',
	PRIMARY KEY (id)
) COMMENT = '资源' DEFAULT CHARACTER SET utf8;


-- 角色资源
CREATE TABLE sys_resource_permission
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 角色
	role_id bigint(21) unsigned NOT NULL COMMENT '角色',
	-- 资源
	resource_id bigint(21) unsigned NOT NULL COMMENT '资源',
	PRIMARY KEY (id)
) COMMENT = '角色资源' DEFAULT CHARACTER SET utf8;


-- 角色
CREATE TABLE sys_role
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 公司
	company_id bigint(21) unsigned NOT NULL COMMENT '公司',
	-- 编号
	code varchar(64) NOT NULL COMMENT '编号',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' NOT NULL COMMENT '禁用',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '角色' DEFAULT CHARACTER SET utf8;


-- 路由
CREATE TABLE sys_router
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 编号
	code varchar(64) COMMENT '编号',
	-- URL
	url varchar(128) COMMENT 'URL',
	-- 父级
	parent_id bigint(21) unsigned NOT NULL COMMENT '父级',
	-- 全标识
	ids varchar(2048) NOT NULL COMMENT '全标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 排序
	sort bigint(21) unsigned DEFAULT 0 NOT NULL COMMENT '排序',
	-- 可以授权
	can_permission varchar(1) DEFAULT '1' NOT NULL COMMENT '可以授权',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' NOT NULL COMMENT '禁用',
	-- 扩展属性
	properties varchar(1024) NOT NULL COMMENT '扩展属性',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	-- 系统标识
	system_code varchar(64) NOT NULL COMMENT '系统标识',
	-- 图片地址
	icon_id bigint(21) unsigned COMMENT '图片地址',
	PRIMARY KEY (id)
) COMMENT = '路由' DEFAULT CHARACTER SET utf8;


-- 角色路由
CREATE TABLE sys_router_permission
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 角色
	role_id bigint(21) unsigned NOT NULL COMMENT '角色',
	-- 路由
	router_id bigint(21) unsigned NOT NULL COMMENT '路由',
	PRIMARY KEY (id)
) COMMENT = '角色路由' DEFAULT CHARACTER SET utf8;


CREATE TABLE sys_seting
(
	-- 标识
	id bigint(21) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识',
	sys_name varchar(255),
	sys_abbrname varchar(128),
	-- 登录背景色
	login_bgcolor varchar(20) COMMENT '登录背景色',
	-- 登陆布局
	login_layout varchar(20) COMMENT '登陆布局',
	-- 项目标志
	project_logo bigint(21) unsigned COMMENT '项目标志',
	-- 登录标志
	login_logo bigint(21) unsigned COMMENT '登录标志',
	-- 系统标志
	sys_logo bigint(21) unsigned COMMENT '系统标志',
	-- 系统图标
	favicon bigint(21) unsigned COMMENT '系统图标',
	-- 登录背景图
	login_bg bigint(21) unsigned COMMENT '登录背景图',
	-- 登录配图
	login_graph bigint(21) unsigned COMMENT '登录配图',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id),
	UNIQUE (id)
) DEFAULT CHARACTER SET utf8;


-- 系统主题
CREATE TABLE sys_theme
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 主题
	theme text NOT NULL COMMENT '主题',
	-- 默认
	is_default varchar(1) DEFAULT '1' COMMENT '默认',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '系统主题' DEFAULT CHARACTER SET utf8;


-- 用户
CREATE TABLE sys_user
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 公司
	company_id bigint(21) unsigned COMMENT '公司',
	-- 部门
	department_id bigint(21) unsigned COMMENT '部门',
	-- 名称
	name varchar(128) COMMENT '名称',
	-- 登录账号
	login_name varchar(10) NOT NULL COMMENT '登录账号',
	-- 密码
	login_password varchar(64) COMMENT '密码',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' NOT NULL COMMENT '禁用',
	-- 手机号
	phone varchar(20) NOT NULL COMMENT '手机号',
	-- 邮箱地址
	email varchar(36) NOT NULL COMMENT '邮箱地址',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '用户' DEFAULT CHARACTER SET utf8;


-- 用户角色
CREATE TABLE sys_user_role
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 标识
	user_id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 角色
	role_id bigint(21) unsigned NOT NULL COMMENT '角色',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '用户角色' DEFAULT CHARACTER SET utf8;


CREATE TABLE ten_tenant
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 名称
	name varchar(128) NOT NULL COMMENT '名称',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' NOT NULL COMMENT '禁用',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


CREATE TABLE ten_tenant_admin
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 租户
	tenant_id bigint(21) unsigned NOT NULL COMMENT '租户',
	-- 用户
	user_id bigint(21) unsigned NOT NULL COMMENT '用户',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


-- 租户应用版本
CREATE TABLE ten_tenant_app
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 租户
	tenant_id bigint(21) unsigned NOT NULL COMMENT '租户',
	-- 应用版本
	app_version_id bigint NOT NULL COMMENT '应用版本',
	-- 禁用
	is_locked varchar(1) DEFAULT '0' NOT NULL COMMENT '禁用',
	-- 数据库IP
	db_ip varchar(128) NOT NULL COMMENT '数据库IP',
	-- 数据库端口
	db_port int unsigned NOT NULL COMMENT '数据库端口',
	-- 数据库名
	db_name varchar(64) NOT NULL COMMENT '数据库名',
	-- 数据库用户名
	db_username varchar(64) NOT NULL COMMENT '数据库用户名',
	-- 数据库密码
	db_password varchar(64) NOT NULL COMMENT '数据库密码',
	-- 数据库URL
	db_url varchar(512) NOT NULL COMMENT '数据库URL',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '租户应用版本' DEFAULT CHARACTER SET utf8;


CREATE TABLE user_grid
(
	-- 标识
	id bigint(21) unsigned NOT NULL COMMENT '标识',
	-- 用户id
	user_id bigint(21) unsigned NOT NULL COMMENT '用户id',
	-- 路由id
	router_id bigint(21) unsigned NOT NULL COMMENT '路由id',
	grid_info varchar(1000),
	-- 选中展示的模块id
	default_check varchar(200) COMMENT '选中展示的模块id',
	-- 备注信息
	remarks varchar(255) COMMENT '备注信息',
	-- 创建者
	create_by varchar(64) NOT NULL COMMENT '创建者',
	-- 创建时间
	create_date varchar(24) NOT NULL COMMENT '创建时间',
	-- 更新者
	update_by varchar(64) NOT NULL COMMENT '更新者',
	-- 更新时间
	update_date varchar(24) NOT NULL COMMENT '更新时间',
	-- 删除标记（0：正常；1：删除）
	del_flag varchar(1) DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除）',
	PRIMARY KEY (id)
) DEFAULT CHARACTER SET utf8;


CREATE TABLE worker_node
(
	-- auto increment id
	ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
	-- host name
	HOST_NAME varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'host name',
	-- port
	PORT varchar(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT 'port',
	-- node type: ACTUAL or CONTAINER
	TYPE int NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
	-- launch date
	LAUNCH_DATE date NOT NULL COMMENT 'launch date',
	-- modified time
	MODIFIED timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'modified time',
	-- created time
	CREATED timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'created time',
	PRIMARY KEY (ID)
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;



/* Create Foreign Keys */

ALTER TABLE dc_column_view
	ADD FOREIGN KEY (dc_table_id)
	REFERENCES dc_table (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE notice_receive_site
	ADD CONSTRAINT notice_receive_site_ibfk_1 FOREIGN KEY (send_id)
	REFERENCES notice_send (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE gen_company_app
	ADD FOREIGN KEY (company_id)
	REFERENCES org_company (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE org_department
	ADD FOREIGN KEY (company_id)
	REFERENCES org_company (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_action_recycle
	ADD FOREIGN KEY (action_id)
	REFERENCES sys_action (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_dict_item
	ADD FOREIGN KEY (dict_type_id)
	REFERENCES sys_dict_type (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_file_content
	ADD FOREIGN KEY (id)
	REFERENCES sys_file (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_resource_permission
	ADD FOREIGN KEY (resource_id)
	REFERENCES sys_resource (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_data_permission
	ADD FOREIGN KEY (role_id)
	REFERENCES sys_role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_resource_permission
	ADD FOREIGN KEY (role_id)
	REFERENCES sys_role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_router_permission
	ADD FOREIGN KEY (role_id)
	REFERENCES sys_role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_user_role
	ADD FOREIGN KEY (role_id)
	REFERENCES sys_role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_data_permission
	ADD FOREIGN KEY (router_id)
	REFERENCES sys_router (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_resource
	ADD FOREIGN KEY (router_id)
	REFERENCES sys_router (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_router_permission
	ADD FOREIGN KEY (router_id)
	REFERENCES sys_router (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sys_user_role
	ADD FOREIGN KEY (user_id)
	REFERENCES sys_user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ten_tenant_admin
	ADD FOREIGN KEY (user_id)
	REFERENCES sys_user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ten_tenant_admin
	ADD FOREIGN KEY (tenant_id)
	REFERENCES ten_tenant (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ten_tenant_app
	ADD FOREIGN KEY (tenant_id)
	REFERENCES ten_tenant (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE gen_company_app
	ADD FOREIGN KEY (tenant_app_version_id)
	REFERENCES ten_tenant_app (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE INDEX send_id USING BTREE ON notice_receive_site (send_id ASC);
CREATE INDEX receive_by USING BTREE ON notice_receive_site (receive_by ASC);
CREATE INDEX receiver USING BTREE ON notice_record_email (receiver ASC);
CREATE INDEX sender_id USING BTREE ON notice_send (sender_id ASC);
CREATE INDEX user USING BTREE ON notice_unsubscribe (user_id ASC);
CREATE INDEX org_company_parent ON org_company (parent_id ASC);
CREATE INDEX org_department_parent ON org_department (parent_id ASC);
CREATE INDEX sys_collect_user_id ON sys_collect (user_id ASC);
CREATE INDEX sys_user_login_name ON sys_user (login_name ASC);



