<template>
  <div class="source">
    <van-nav-bar title="入职信息" />

    <div
      v-for="(item, index) in sourceOptions"
      :key="index"
      class="entry-source"
      @click="onUploadViewFile(item)"
    >
      <div class="image">
        <img
          width="67"
          height="67"
          src="~@/assets/img/pdf.jpg"
          style="border-radius: 5px;"
          v-if="item.attribute0.indexOf('.pdf') > 0"
        />
        <img
          width="67"
          height="67"
          src="~@/assets/img/word.jpg"
          style="border-radius: 5px;"
          v-else-if="
            item.attribute0.indexOf('.docx') > 0 ||
              item.attribute0.indexOf('.doc') > 0
          "
        />
        <img
          width="67"
          height="67"
          src="~@/assets/img/pic.jpg"
          style="border-radius: 5px;"
          v-else
        />
      </div>

      <span style="font-size: 14px;margin-left:10%;width:60%">
        {{ item.attribute0 }}</span
      >
    </div>

    <van-button round block @click="downloadAll" class="back-button"
      >全部下载</van-button
    >
    <van-button round block type="info" @click="back" class="back-button"
      >返回首页</van-button
    >
  </div>
</template>
<script>
import { listUserPropDetailsAll } from "@/api/induction/inductApp";
import { listSysFileAll, fileUploadById } from "@/api/sys/sysFile";
export default {
  data() {
    return {
      sourceOptions: []
    };
  },
  mounted() {
    this.getSource();
  },
  methods: {
    back() {
      this.$router.push({
        path: "/main"
      });
    },
    downloadAll() {
      this.sourceOptions.forEach(item => {
        this.onUploadViewFile(item);
      });
    },
    onUploadViewFile(row) {
      // 下载
      let file_search = {
        params: [
          { columnName: "object_id", queryType: "=", value: row.attribute1 }
        ]
      };
      listSysFileAll(file_search).then(res => {
        if (res.code === "100") {
          if (res.data.length === 0) {
            this.$message({
              message: "该数据还未有文件可下载"
            });
          } else {
            res.data.forEach(item => {
              fileUploadById(item.id)
                .then(responseData => {
                  const content = responseData;
                  this.download(content, item.name);
                })
                .catch(error => {
                  this.outputError(error);
                });
            });
          }
        }
      });
    },
    //下载方法
    download(content, fileName) {
      const blob = new Blob([content]);
      const url = window.URL.createObjectURL(blob);
      let dom = document.createElement("a");
      dom.style.display = "none";
      dom.href = url;
      dom.setAttribute("download", fileName);
      document.body.appendChild(dom);
      dom.click();
    },
    documentAll() {},
    //获取入职资料包
    getSource() {
      let search = {
        columnName: "",
        order: "",
        params: [
          { columnName: "user_prop_list_id", queryType: "=", value: "3" }
        ]
      };
      listUserPropDetailsAll(search).then(res => {
        if (res.code === "100") {
          this.sourceOptions = res.data.filter(item => item.used === "1");
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.source {
  height: 100vh;

  .entry-source {
    width: 90%;
    margin: 5%;
    height: 81px;
    background: #ffffff;
    border-radius: 5px;
    display: flex;
    align-items: center;
    .image {
      width: 40%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }

  .back-button {
    width: 90%;
    margin: 20px 5%;
  }
}
</style>
