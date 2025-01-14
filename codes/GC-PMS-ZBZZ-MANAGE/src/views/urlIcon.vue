<template>
  <div class="url-icon-body">
    <div class="url-icon-element" @click="toLink">
      <i :class="icon"></i>
    </div>
    <div style="height: 60px;display: flex;">
      <span style="margin: 0 auto;color: #1ab394">{{ name }}</span>
    </div>
  </div>
</template>

<script>export default {
  name: "url-icon",
  props: {
    name: {
      type: String,
      default: ""
    },
    url: {
      type: String,
      default: ""
    },
    icon: {
      type: String,
      default: "el-icon-s-promotion"
    }
  },
  data() {
    return {}
  },
  methods: {
    toLink() {
      if (this.url) {
        window.open(this.url)
      }
    }
  }
}
</script>

<style scoped>
.url-icon-element {
  height: 100px;
  width: 100px;
  background-color: #fff;
  border-radius: 25px;
  margin: 10px auto 10px;
  text-align: center;
  line-height: 80px;
  color: #1e1e1e;
  padding: 10px;
  font-size: 50px;
}

.url-icon-body {
  width: 180px;
  height: 180px;
  display: inline-block;
  margin: 10px;
}

.url-icon-element:hover {
  cursor: pointer;
  /* 阴影 */
  box-shadow: 0 10px 15px rgba(145, 224, 254, 0.2);
}
</style>
