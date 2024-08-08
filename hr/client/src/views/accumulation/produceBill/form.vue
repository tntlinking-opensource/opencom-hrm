<template>
  <van-row class="dm-container">
    <el-dialog v-on:close="onDialogClose" v-loading="loading" width="50%" :fullscreen="true" title="查看账单"
      :visible.sync="visible" :close-on-click-modal="false" class="dc-el-dialog_ElDialog">
      <header><span>批次号:{{ rowData['batchNo'] }}</span><span style="margin-left: 50px;">账单类型:<el-radio
            style="margin-left: 10px;" v-model="radio" label="0">汇总账单</el-radio><el-radio v-model="radio"
            label="1">明细账单</el-radio></span></header>
      <div v-if="radio === '0'">
        <el-button type="primary" icon="el-icon-download" class="dc-el-button_Button" style="margin: 20px;margin-left: 0;"
          @click="exportData">导出</el-button>
        <ux-grid column-key ref="table" :data="tableData" border
          :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
          class="dc-ux-grid_QueryTable">
          <ux-table-column :field="tableColumns['129'].field" :title="tableColumns['129'].title"
            :width="tableColumns['129'].width" :visible="tableColumns['129'].visible" min-width="120px"
            :params="{ sortId: '129', summary: false }" tree-node resizable align="center" header-align="center" sortable
            show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['130'].field" :title="tableColumns['130'].title"
            :width="tableColumns['130'].width" :visible="tableColumns['130'].visible"
            :params="{ sortId: '130', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['131'].field" :title="tableColumns['131'].title"
            :width="tableColumns['131'].width" :visible="tableColumns['131'].visible"
            :params="{ sortId: '131', summary: false }" tree-node resizable min-width="180px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['132'].field" :title="tableColumns['132'].title"
            :width="tableColumns['132'].width" :visible="tableColumns['132'].visible"
            :params="{ sortId: '132', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['133'].field" :title="tableColumns['133'].title"
            :width="tableColumns['133'].width" :visible="tableColumns['133'].visible"
            :params="{ sortId: '133', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['134'].field" :title="tableColumns['134'].title"
            :width="tableColumns['134'].width" :visible="tableColumns['134'].visible"
            :params="{ sortId: '134', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['135'].field" :title="tableColumns['135'].title"
            :width="tableColumns['135'].width" :visible="tableColumns['135'].visible"
            :params="{ sortId: '135', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['136'].field" :title="tableColumns['136'].title"
            :width="tableColumns['136'].width" :visible="tableColumns['136'].visible"
            :params="{ sortId: '136', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['137'].field" :title="tableColumns['137'].title"
            :width="tableColumns['137'].width" :visible="tableColumns['137'].visible"
            :params="{ sortId: '137', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['138'].field" :title="tableColumns['138'].title"
            :width="tableColumns['138'].width" :visible="tableColumns['138'].visible"
            :params="{ sortId: '138', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns['139'].field" :title="tableColumns['139'].title"
            :width="tableColumns['139'].width" :visible="tableColumns['139'].visible"
            :params="{ sortId: '139', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-grid>

      </div>
      <div v-if="radio === '1'">
        员工姓名/工号：<el-input v-model="employeeNoOrName" :maxLength="64" style="width:300px" placeholder="请输入" clearable
          autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
        <el-button icon="el-icon-search" class="dc-el-button_Button" style="margin: 20px;"
          @click="getTableData">查询</el-button>
        <el-button @click="exportData" type="primary" icon="el-icon-download" class="dc-el-button_Button">导出</el-button>
      </div>
      <ux-grid column-key ref="table" v-if="radio === '1'" :data="tableData" border show-summary
        :summary-method="summaryMethod" :total-option="totalOption"
        :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
        class="dc-ux-grid_QueryTable">
        <ux-table-column :field="tableColumns1['150'].field" :title="tableColumns1['150'].title" fixed="left"
          :width="tableColumns1['150'].width" :visible="tableColumns1['150'].visible" min-width="120px"
          :params="{ sortId: '150', summary: false }" tree-node resizable align="center" header-align="center" sortable
          show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['151'].field" :title="tableColumns1['151'].title" fixed="left"
          :width="tableColumns1['151'].width" :visible="tableColumns1['151'].visible"
          :params="{ sortId: '151', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['153'].field" :title="tableColumns1['153'].title" fixed="left"
          :width="tableColumns1['153'].width" :visible="tableColumns1['153'].visible"
          :params="{ sortId: '153', summary: false }" tree-node resizable min-width="180px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['152'].field" :title="tableColumns1['152'].title" fixed="left"
          :width="tableColumns1['152'].width" :visible="tableColumns1['152'].visible"
          :params="{ sortId: '153', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['154'].field" :title="tableColumns1['154'].title" fixed="left"
          :width="tableColumns1['154'].width" :visible="tableColumns1['154'].visible"
          :params="{ sortId: '154', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['155'].field" :title="tableColumns1['155'].title" fixed="left"
          :width="tableColumns1['155'].width" :visible="tableColumns1['155'].visible"
          :params="{ sortId: '155', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>

        <ux-table-column title="养老保险" align="center">
          <ux-table-column :field="tableColumns1['156'].field" :title="tableColumns1['156'].title"
            :width="tableColumns1['156'].width" :visible="tableColumns1['156'].visible"
            :params="{ sortId: '156', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['157'].field" :title="tableColumns1['157'].title"
            :width="tableColumns1['157'].width" :visible="tableColumns1['157'].visible"
            :params="{ sortId: '157', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['158'].field" :title="tableColumns1['158'].title"
            :width="tableColumns1['158'].width" :visible="tableColumns1['158'].visible"
            :params="{ sortId: '158', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['159'].field" :title="tableColumns1['159'].title"
            :width="tableColumns1['159'].width" :visible="tableColumns1['159'].visible"
            :params="{ sortId: '159', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['160'].field" :title="tableColumns1['160'].title"
            :width="tableColumns1['160'].width" :visible="tableColumns1['160'].visible"
            :params="{ sortId: '160', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['161'].field" :title="tableColumns1['161'].title"
            :width="tableColumns1['161'].width" :visible="tableColumns1['161'].visible"
            :params="{ sortId: '161', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>
        <ux-table-column title="大病保险" align="center">
          <ux-table-column :field="tableColumns1['180'].field" :title="tableColumns1['180'].title"
            :width="tableColumns1['180'].width" :visible="tableColumns1['180'].visible"
            :params="{ sortId: '180', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['181'].field" :title="tableColumns1['181'].title"
            :width="tableColumns1['181'].width" :visible="tableColumns1['181'].visible"
            :params="{ sortId: '181', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['182'].field" :title="tableColumns1['182'].title"
            :width="tableColumns1['182'].width" :visible="tableColumns1['182'].visible"
            :params="{ sortId: '182', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['183'].field" :title="tableColumns1['183'].title"
            :width="tableColumns1['183'].width" :visible="tableColumns1['183'].visible"
            :params="{ sortId: '183', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['184'].field" :title="tableColumns1['184'].title"
            :width="tableColumns1['184'].width" :visible="tableColumns1['184'].visible"
            :params="{ sortId: '184', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['185'].field" :title="tableColumns1['185'].title"
            :width="tableColumns1['185'].width" :visible="tableColumns1['185'].visible"
            :params="{ sortId: '185', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>

        <ux-table-column title="失业保险" align="center">
          <ux-table-column :field="tableColumns1['168'].field" :title="tableColumns1['168'].title"
            :width="tableColumns1['168'].width" :visible="tableColumns1['168'].visible"
            :params="{ sortId: '168', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['169'].field" :title="tableColumns1['169'].title"
            :width="tableColumns1['169'].width" :visible="tableColumns1['169'].visible"
            :params="{ sortId: '169', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['170'].field" :title="tableColumns1['170'].title"
            :width="tableColumns1['170'].width" :visible="tableColumns1['170'].visible"
            :params="{ sortId: '170', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['171'].field" :title="tableColumns1['171'].title"
            :width="tableColumns1['171'].width" :visible="tableColumns1['171'].visible"
            :params="{ sortId: '171', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['172'].field" :title="tableColumns1['172'].title"
            :width="tableColumns1['172'].width" :visible="tableColumns1['172'].visible"
            :params="{ sortId: '172', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['173'].field" :title="tableColumns1['173'].title"
            :width="tableColumns1['173'].width" :visible="tableColumns1['173'].visible"
            :params="{ sortId: '173', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>
        <ux-table-column title="工伤保险" align="center">
          <ux-table-column :field="tableColumns1['174'].field" :title="tableColumns1['174'].title"
            :width="tableColumns1['174'].width" :visible="tableColumns1['174'].visible"
            :params="{ sortId: '174', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['175'].field" :title="tableColumns1['175'].title"
            :width="tableColumns1['175'].width" :visible="tableColumns1['175'].visible"
            :params="{ sortId: '175', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['176'].field" :title="tableColumns1['176'].title"
            :width="tableColumns1['176'].width" :visible="tableColumns1['176'].visible"
            :params="{ sortId: '176', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['177'].field" :title="tableColumns1['177'].title"
            :width="tableColumns1['177'].width" :visible="tableColumns1['177'].visible"
            :params="{ sortId: '177', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['178'].field" :title="tableColumns1['178'].title"
            :width="tableColumns1['178'].width" :visible="tableColumns1['178'].visible"
            :params="{ sortId: '178', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['179'].field" :title="tableColumns1['179'].title"
            :width="tableColumns1['179'].width" :visible="tableColumns1['179'].visible"
            :params="{ sortId: '179', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>

        <ux-table-column title="医疗保险" align="center">
          <ux-table-column :field="tableColumns1['162'].field" :title="tableColumns1['162'].title"
            :width="tableColumns1['162'].width" :visible="tableColumns1['162'].visible"
            :params="{ sortId: '162', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['163'].field" :title="tableColumns1['163'].title"
            :width="tableColumns1['163'].width" :visible="tableColumns1['163'].visible"
            :params="{ sortId: '163', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['164'].field" :title="tableColumns1['164'].title"
            :width="tableColumns1['164'].width" :visible="tableColumns1['164'].visible"
            :params="{ sortId: '164', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['165'].field" :title="tableColumns1['165'].title"
            :width="tableColumns1['165'].width" :visible="tableColumns1['165'].visible"
            :params="{ sortId: '165', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['166'].field" :title="tableColumns1['166'].title"
            :width="tableColumns1['166'].width" :visible="tableColumns1['166'].visible"
            :params="{ sortId: '166', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['167'].field" :title="tableColumns1['167'].title"
            :width="tableColumns1['167'].width" :visible="tableColumns1['167'].visible"
            :params="{ sortId: '167', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>
        <ux-table-column title="生育保险" align="center">
          <ux-table-column :field="tableColumns1['186'].field" :title="tableColumns1['186'].title"
            :width="tableColumns1['186'].width" :visible="tableColumns1['186'].visible"
            :params="{ sortId: '186', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['187'].field" :title="tableColumns1['187'].title"
            :width="tableColumns1['187'].width" :visible="tableColumns1['187'].visible"
            :params="{ sortId: '187', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['188'].field" :title="tableColumns1['188'].title"
            :width="tableColumns1['188'].width" :visible="tableColumns1['188'].visible"
            :params="{ sortId: '188', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['189'].field" :title="tableColumns1['189'].title"
            :width="tableColumns1['189'].width" :visible="tableColumns1['189'].visible"
            :params="{ sortId: '189', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['190'].field" :title="tableColumns1['190'].title"
            :width="tableColumns1['190'].width" :visible="tableColumns1['190'].visible"
            :params="{ sortId: '190', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['191'].field" :title="tableColumns1['191'].title"
            :width="tableColumns1['191'].width" :visible="tableColumns1['191'].visible"
            :params="{ sortId: '191', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>
        <ux-table-column title="住房公积金" align="center">
          <ux-table-column :field="tableColumns1['192'].field" :title="tableColumns1['192'].title"
            :width="tableColumns1['192'].width" :visible="tableColumns1['192'].visible"
            :params="{ sortId: '192', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['193'].field" :title="tableColumns1['193'].title"
            :width="tableColumns1['193'].width" :visible="tableColumns1['193'].visible"
            :params="{ sortId: '193', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['194'].field" :title="tableColumns1['194'].title"
            :width="tableColumns1['194'].width" :visible="tableColumns1['194'].visible"
            :params="{ sortId: '194', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['195'].field" :title="tableColumns1['195'].title"
            :width="tableColumns1['195'].width" :visible="tableColumns1['195'].visible"
            :params="{ sortId: '195', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['196'].field" :title="tableColumns1['196'].title"
            :width="tableColumns1['196'].width" :visible="tableColumns1['196'].visible"
            :params="{ sortId: '196', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
          <ux-table-column :field="tableColumns1['197'].field" :title="tableColumns1['197'].title"
            :width="tableColumns1['197'].width" :visible="tableColumns1['197'].visible"
            :params="{ sortId: '197', summary: false }" tree-node resizable min-width="120px" align="center"
            header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        </ux-table-column>
        <ux-table-column :field="tableColumns1['250'].field" :title="tableColumns1['250'].title"
          :width="tableColumns1['250'].width" :visible="tableColumns1['250'].visible"
          :params="{ sortId: '250', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>

        <ux-table-column :field="tableColumns1['254'].field" :title="tableColumns1['254'].title"
          :width="tableColumns1['254'].width" :visible="tableColumns1['254'].visible"
          :params="{ sortId: '254', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['255'].field" :title="tableColumns1['255'].title"
          :width="tableColumns1['255'].width" :visible="tableColumns1['255'].visible"
          :params="{ sortId: '255', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['253'].field" :title="tableColumns1['253'].title"
          :width="tableColumns1['253'].width" :visible="tableColumns1['253'].visible"
          :params="{ sortId: '253', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['256'].field" :title="tableColumns1['256'].title"
          :width="tableColumns1['256'].width" :visible="tableColumns1['256'].visible"
          :params="{ sortId: '256', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="tableColumns1['257'].field" :title="tableColumns1['257'].title"
          :width="tableColumns1['257'].width" :visible="tableColumns1['257'].visible"
          :params="{ sortId: '257', summary: false }" tree-node resizable min-width="120px" align="center"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      </ux-grid>

      <el-pagination :total="tableDataTotal" :page-size="search.limit" background :current-page="tableDataPage"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
        v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
        class="dc-el-pagination_ElPagination"></el-pagination>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onDialogClose">返回</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible" class="uploadDialog"> </el-dialog>
  </van-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import {
  getBillDetailsList, exportBillDetails
} from '@/api/socialSecurityFund/socialSecurityFund'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'

export default {
  extends: BaseUI,
  name: 'produceBill-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      // 对话框属性变量
      visible: false,
      radio: '0',
      rowData: {},
      tableData: [],
      tableDataTotal: 0,
      tableDataPage: 1,
      // 选项变量
      /** 根据用户界面配置生成data数据 结束 */
      version: 3,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      employeeNoOrName: '',
      tableColumns: {
        129: {
          title: '账单年月',
          field: 'billYearMonth',
          visible: true,
          order: 0
        },
        130: {
          title: '单位社保',
          field: 'unitSocialSecurityAmount',
          visible: true,
          order: 1
        },
        131: {
          title: '个人社保',
          field: 'personSocialSecurityAmount',
          visible: true,
          order: 2
        },
        132: {
          title: '社保小计',
          field: 'totalSocialSecurityAmount',
          visible: true,
          order: 3
        },
        133: {
          title: '单位公积金',
          field: 'unitAccumulaHund',
          visible: true,
          order: 4
        },

        134: {
          title: '个人公积金',
          field: 'personAccumulaHund',
          visible: true,
          order: 5
        },
        135: {
          title: '公积金小计',
          field: 'totalAccumulaHundAmount',
          visible: true,
          order: 6
        },
        136: {
          title: '残疾人基金',
          field: 'unitDisabledHund',
          visible: true,
          order: 7
        },
        137: {
          title: '管理费',
          field: 'managementAmount',
          visible: true,
          order: 8
        },
        138: {
          title: '税金',
          field: 'taxAmount',
          visible: true,
          order: 9
        },
        139: {
          title: '合计',
          field: 'totalAmount',
          visible: true,
          order: 10
        },
      },
      tableColumns1: {
        150: {
          title: '缴费城市',
          field: 'cityName',
          visible: true,
          order: 0
        },
        151: {
          title: '员工工号',
          field: 'employeeNo',
          visible: true,
          order: 1
        },
        152: {
          title: '员工姓名',
          field: 'employeeName',
          visible: true,
          order: 2
        },
        153: {
          title: '身份证号',
          field: 'idCardNo',
          visible: true,
          order: 3
        },
        154: {
          title: '部门',
          field: 'deptName',
          visible: true,
          order: 4
        },
        155: {
          title: '月份',
          field: 'billYearMonth',
          visible: true,
          order: 5
        },
        156: {
          title: '基数',
          field: 'annuityBase',
          visible: true,
          order: 6
        },
        157: {
          title: '总额',
          field: 'annuityAmount',
          visible: true,
          order: 7
        },
        158: {
          title: '单位比例',
          field: 'unitAnnuityScale',
          visible: true,
          order: 8
        },
        159: {
          title: '单位金额',
          field: 'unitAnnuityAmount',
          visible: true,
          order: 9
        },
        160: {
          title: '个人比例',
          field: 'personAnnuityScale',
          visible: true,
          order: 10
        },
        161: {
          title: '个人金额',
          field: 'personAnnuityAmount',
          visible: true,
          order: 11
        },
        162: {
          title: '基数',
          field: 'medicalBase',
          visible: true,
          order: 6
        },
        163: {
          title: '总额',
          field: 'medicalAmount',
          visible: true,
          order: 7
        },
        164: {
          title: '单位比例',
          field: 'unitMedicalScale',
          visible: true,
          order: 8
        },
        165: {
          title: '单位金额',
          field: 'unitMedicalAmount',
          visible: true,
          order: 9
        },
        166: {
          title: '个人比例',
          field: 'personMedicalScale',
          visible: true,
          order: 10
        },
        167: {
          title: '个人金额',
          field: 'personMedicalAmount',
          visible: true,
          order: 11
        },
        168: {
          title: '基数',
          field: 'unemployBase',
          visible: true,
          order: 6
        },
        169: {
          title: '总额',
          field: 'unemployAmount',
          visible: true,
          order: 7
        },
        170: {
          title: '单位比例',
          field: 'unitUnemployScale',
          visible: true,
          order: 8
        },
        171: {
          title: '单位金额',
          field: 'unitUnemployAmount',
          visible: true,
          order: 9
        },
        172: {
          title: '个人比例',
          field: 'personUnemployScale',
          visible: true,
          order: 10
        },
        173: {
          title: '个人金额',
          field: 'personUnemployAmount',
          visible: true,
          order: 11
        },
        174: {
          title: '基数',
          field: 'injuryBase',
          visible: true,
          order: 6
        },
        175: {
          title: '总额',
          field: 'injuryAmount',
          visible: true,
          order: 7
        },
        176: {
          title: '单位比例',
          field: 'unitInjuryScale',
          visible: true,
          order: 8
        },
        177: {
          title: '单位金额',
          field: 'unitInjuryAmount',
          visible: true,
          order: 9
        },
        178: {
          title: '个人比例',
          field: 'personInjuryScale',
          visible: true,
          order: 10
        },
        179: {
          title: '个人金额',
          field: 'personInjuryAmount',
          visible: true,
          order: 11
        },
        180: {
          title: '基数',
          field: 'illMedicalBase',
          visible: true,
          order: 6
        },
        181: {
          title: '总额',
          field: 'illMedicalAmount',
          visible: true,
          order: 7
        },
        182: {
          title: '单位比例',
          field: 'unitIllMedicalScale',
          visible: true,
          order: 8
        },
        183: {
          title: '单位金额',
          field: 'unitIllMedicalAmount',
          visible: true,
          order: 9
        },
        184: {
          title: '个人比例',
          field: 'personIllMedicalScale',
          visible: true,
          order: 10
        },
        185: {
          title: '个人金额',
          field: 'personIllMedicalAmount',
          visible: true,
          order: 11
        },
        186: {
          title: '基数',
          field: 'bearBase',
          visible: true,
          order: 6
        },
        187: {
          title: '总额',
          field: 'bearAmount',
          visible: true,
          order: 7
        },
        188: {
          title: '单位比例',
          field: 'unitBearScale',
          visible: true,
          order: 8
        },
        189: {
          title: '单位金额',
          field: 'unitBearAmount',
          visible: true,
          order: 9
        },
        190: {
          title: '个人比例',
          field: 'personBearScale',
          visible: true,
          order: 10
        },
        191: {
          title: '个人金额',
          field: 'personBearAmount',
          visible: true,
          order: 11
        },
        192: {
          title: '基数',
          field: 'accumulaHundBase',
          visible: true,
          order: 6
        },
        193: {
          title: '总额',
          field: 'accumulaHundAmount',
          visible: true,
          order: 7
        },
        194: {
          title: '单位比例',
          field: 'unitAccumulaHundScale',
          visible: true,
          order: 8
        },
        195: {
          title: '单位金额',
          field: 'unitAccumulaHundAmount',
          visible: true,
          order: 9
        },
        196: {
          title: '个人比例',
          field: 'personAccumulaHundScale',
          visible: true,
          order: 10
        },
        197: {
          title: '个人金额',
          field: 'personAccumulaHundAmount',
          visible: true,
          order: 11
        },
        250: {
          title: '残疾人基金',
          field: 'unitDisabledHund',
          visible: true,
          order: 250
        },
        253: {
          title: '个人社保小计',
          field: 'personSocialSecurityAmount',
          visible: true,
          order: 253
        },
        254: {
          title: '单位社保小计',
          field: 'unitSocialSecurityAmount',
          visible: true,
          order: 254
        },
        255: {
          title: '单位公积金小计',
          field: 'unitAccumulaHundAmount',
          visible: true,
          order: 255
        },
        256: {
          title: '个人公积金小计',
          field: 'personAccumulaHund',
          visible: true,
          order: 255
        },
        257: {
          title: '合计',
          field: 'amount',
          visible: true,
          order: 255
        },
      },
      dialogVisible: false
    }
  },
  filters: {
    getFloat(val) {
      if (val) {
        return val.toFixed(2)
      }
      if (val === 0) {
        return '0.00'
      }
      return null
    }
  },
  watch: {
    radio(val) {
      this.initData()
    }
  },

  methods: {
    onDialogClose() {
      this.visible = false
    },
    summaryMethod({ columns, data }) {

      const means = [] // 合计
      columns.forEach((column, columnIndex) => {
        if (columnIndex === 0) {
          means.push('合计')
        } else {
          const values = data.map(item => Number(item[column.property]));
          // 合计
          if (!values.every(value => isNaN(value))) {
            means[columnIndex] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            if (column.title === '总额' || column.title === '单位金额' || column.title === '个人金额' ||
              column.title === '残疾人基金' || column.title === '管理费' || column.title === '税金' ||
              column.title === '单位社保小计' || column.title === '个人社保小计' || column.title === '单位公积金小计' ||
              column.title === '个人公积金小计' || column.title === '合计') {
              means[columnIndex]
            } else {
              means[columnIndex] = '';
            }
          } else {
            means[columnIndex] = '';
          }
        }
      })
      // 返回一个二维数组的表尾合计(不要平均值，你就不要在数组中添加)
      return [means]

    },
    initData() {
      this.employeeNoOrName = ''
      this.tableDataPage = 1
      this.search.limit = 10;
      this.tableData = []
      this.tableDataTotal = 0,
        this.search.offset = 0
      this.getTableData()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getTableData()
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val;
      this.search.offset = (this.tableDataPage - 1) * val
      this.getTableData()
    },

    getTableData() {
      let params = {
        batchNo: this.rowData['batchNo'],
        billType: this.radio,
        "offset": Number(this.search.offset),
        "limit": this.search.limit
      }
      if (this.employeeNoOrName) {
        params.employeeNoOrName = this.employeeNoOrName
      }
      getBillDetailsList(params).then(responseData => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          this.tableData = responseData.data.rows ? responseData.data.rows : []
        } else {
          this.showMessage(responseData)
        }
      }).catch(error => {
        this.outputError(error)
      })

    },
    exportData() {
      this.dialogVisible = true
      let params = {
        batchNo: this.rowData['batchNo'],
        billType: this.radio,
        "offset": Number(this.search.offset),
        "limit": this.search.limit
      }
      if (this.employeeNoOrName) {
        params.employeeNoOrName = this.employeeNoOrName
      }
      exportBillDetails(params).then(resp => {
        const blob = new Blob([resp], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = '社保公积金明细账单.xlsx'
        link.click();
        URL.revokeObjectURL(link.href);
        this.showMessage({
          type: 'success',
          msg: '导出成功'
        })
        this.dialogVisible = false
      })
    },
    init(scope) {
      this.rowData = scope
      this.getTableData()
      this.visible = true
    },
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openAddDialog', function (scope) {
        this.rowData = scope.row
        this.getTableData()
        this.visible = true
      })
    })
  }
}
</script>
<style>
.uploadDialog .el-dialog {
  display: none;
}
</style>
