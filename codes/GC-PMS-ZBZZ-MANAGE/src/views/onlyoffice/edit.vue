<template>
  <div :style="{height:clientHeight,width:'100%'}">
    <div id="placeholder"></div>
  </div>
</template>

<script>
import {editOfficeFile, previewFile} from "@/api/onlyoffice/onlyoffice";

export default {
  name: 'OnlyOffice',
  data() {
    return {
      clientHeight:'700px',
      docEditor: null, //  文档编辑器
      platform: 'desktop', //  查看平台
    }
  },
  computed: {
    // 文件信息，来自于路由参数query
    fileInfo() {
      return this.$route.query
    }
  },
  created() {
    //设置表格高度对应屏幕高度
    this.$nextTick(() => {
      this.clientHeight = (document.body.clientHeight-90) +'px'
    });
  },
  mounted() {
    this.$nextTick(() => {
      switch (this.fileInfo.ot) {
        // 添加
        // case 'add':
        //   this.initOnlyoffice()
        //   break
        // 编辑
        case 'edit':
          this.editDoc()
          break
        // 详情
        case 'detail':
          this.showDocDetail()
          break
      }
    })

    // the application is loaded into the browser
    let onAppReady = function () {
      // Your code here
    };

    // the document is modified
    let onDocumentStateChange = function (event) {
      // Your code here
    };

    // the user is trying to switch the document from the viewing into the editing mode
    let onRequestEditRights = function () {
      // Your code here
    };

    // an error or some other specific event occurs
    let onError = function (event) {
      // Your code here
    };

    // the document is opened for editing with the old document.key value
    let onOutdatedVersion = function (event) {
      // Your code here
    };

    // replace the link to the document which contains a bookmark
    let replaceActionLink = function (href, linkParam) {
      // Your code here
    };

    // the user is trying to get link for opening the document which contains a bookmark, scrolling to the bookmark position
    let onMakeActionLink = function (event) {
      // Your code here
    };

    // the meta information of the document is changed via the meta command
    let onMetaChange = function (event) {
      // Your code here
    };

    let onRequestInsertImage = function (event) {
      // Your code here
    };

    let onRequestCompareFile = function () {
      // Your code here
    };

    let onRequestMailMergeRecipients = function (event) {
      // Your code here
    };

    // config.events = {
    //   "onAppReady": onAppReady,
    //   "onDocumentStateChange": onDocumentStateChange,
    //   'onRequestEditRights': onRequestEditRights,
    //   "onError": onError,
    //   "onOutdatedVersion": onOutdatedVersion,
    //   "onMakeActionLink": onMakeActionLink,
    //   "onMetaChange": onMetaChange,
    //   "onRequestInsertImage": onRequestInsertImage,
    //   "onRequestCompareFile": onRequestCompareFile,
    //   "onRequestMailMergeRecipients": onRequestMailMergeRecipients,
    // };

  },
  methods: {
    /**
     * 编辑文档
     */
    editDoc() {
      let data = {
        userFileId: this.fileInfo.fileId,
        previewUrl: this.fileInfo.fileUrl
      }
      editOfficeFile(data).then((res) => {
        if (res.code === 200) {
          // let config = {
          // 	...res.data.file,
          // 	type: this.platform
          // }
          this.initDocEditor(res.data.docserviceApiUrl, res.data.file)
        }
      })
    },
    /**
     * 查看文档
     */
    showDocDetail() {
      let data = {
        userFileId: this.fileInfo.fileId,
        previewUrl: this.fileInfo.fileUrl
      }
      previewFile(data).then((res) => {
        if (res.code === 200) {
          // let config = {
          // 	...res.data.file,
          // 	type: this.platform
          // }
          this.initDocEditor(res.data.docserviceApiUrl, res.data.file)
        }
      })
    },
    /**
     * 初始化文档编辑器
     * @param {string} docserviceApiUrl 文档服务API url
     * @param {object} config 文件相关配置信息
     */
    initDocEditor(docserviceApiUrl, config) {

      this.loadOnlyOfficeAPI(docserviceApiUrl).then(() => {
        console.log(config)
        /* global DocsAPI */
        // this.docEditor = new DocsAPI.DocEditor('placeholder', this.myConfig)

        this.docEditor = new DocsAPI.DocEditor('placeholder', {
          ...config,
          editorConfig: {
            ...config.editorConfig,
            lang: 'zh', //  语言设置为中文
            customization: {
              ...config.editorConfig.customization,
              zoom: 100 //  缩放比例为 100
            }
          }
        })

      })
    },
    /**
     * 加载 onlyoffice api
     * @return {Promise} 返回 api 加载状态
     */
    loadOnlyOfficeAPI(src) {
      return new Promise((resolve, reject) => {
        const script = document.createElement('script')
        script.type = 'text/javascript'
        script.src = src
        document.body.appendChild(script)
        script.onload = () => {
          resolve()
        }
        script.onerror = () => {
          reject()
        }
      })
    }
  }
};
</script>

<style scoped>
</style>
