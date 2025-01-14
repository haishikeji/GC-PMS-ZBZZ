<template>
    <ul :class="{'double': isHaveChild(dataArray)}">
      <li v-for="(item, index) in dataArray" :key="index" v-if="!item.level" :class="[{
        'liequal': item.post.trim()==='EHS督导'},{
        'litop': item.post.trim()==='装置经理'
        }]">
        <div class="branch-box" @click.prevent="clickHandle(item)" v-if="item.post.trim() !== 'EHS督导'">
          <div class="branch-title">{{item.post}}</div>
          <img class="branch-pic" :src=item.img>
          <div class="branch-name">{{item.label}}</div>
          <div class="bz-box">
            <i v-if="item.bz1" class="iconfont icon-star"></i>
            <i v-if="item.bz2" class="iconfont icon-round"></i>
            <i v-if="item.bz3" class="iconfont icon-yuanjiaoliujiaoxing"></i>
            <i v-if="item.bz4" class="iconfont icon-plus"></i>
          </div>
        </div>
        <!-- 平级 -->
        <div class="branch-box" :class="item.post.trim() === 'EHS督导' ? 'equal':''" @click.prevent="clickHandle(item)" v-if="item.post.trim() === 'EHS督导'">
          <div class="branch-title">{{item.post}}</div>
          <img class="branch-pic" :src=item.img>
          <div class="branch-name">{{item.label}}</div>
          <div class="bz-box">
            <i v-if="item.bz1" class="iconfont icon-star"></i>
            <i v-if="item.bz2" class="iconfont icon-round"></i>
            <i v-if="item.bz3" class="iconfont icon-yuanjiaoliujiaoxing"></i>
            <i v-if="item.bz4" class="iconfont icon-plus"></i>
          </div>
        </div>
        <template v-if="item.secretary">
          <ul class="level" v-for="(secretary, se) in item.secretary" :key="se">
            <template v-for="(child, cIndex) in secretary">
              <li v-if="child.children" :class="{'odd': ((secretary.length%2) === 1)}" :key="cIndex">
                <div class="branch-box" @click.prevent="clickHandle(child)">
                  <div class="branch-title">{{child.post}}</div>
                  <img class="branch-pic" :src=child.img>
                  <div class="branch-name">{{child.label}}</div>
                  <div class="bz-box">
                    <i v-if="child.bz1" class="iconfont icon-star"></i>
                    <i v-if="child.bz2" class="iconfont icon-round"></i>
                    <i v-if="child.bz3" class="iconfont icon-yuanjiaoliujiaoxing"></i>
                    <i v-if="child.bz4" class="iconfont icon-plus"></i>
                  </div>
                </div>
                <ul :style="{'width': ulWidth + 'px'}">
                  <li v-for="(schild, chIndex) in child.children" :key="chIndex">
                    <div class="branch-box" @click.prevent="clickHandle(schild)">
                      <div class="branch-title">{{schild.post}}</div>
                      <img class="branch-pic" :src=schild.img>
                      <div class="branch-name">{{schild.label}}</div>
                      <div class="bz-box">

                        <i v-if="schild.bz1" class="iconfont icon-star"></i>
                        <i v-if="schild.bz2" class="iconfont icon-round"></i>
                        <i v-if="schild.bz3" class="iconfont icon-yuanjiaoliujiaoxing"></i>
                        <i v-if="schild.bz4" class="iconfont icon-plus"></i>
                      </div>
                    </div>
                  </li>
                </ul>
              </li>
              <li v-else :key="cIndex" :class="{'odd': ((secretary.length%2) === 1&&!child.children)}">
                <div class="branch-box" @click.prevent="clickHandle(child)">
                  <div class="branch-title">{{child.post}}</div>
                  <img class="branch-pic" :src=child.img>
                  <div class="branch-name">{{child.label}}</div>
                  <div class="bz-box">
                    <i v-if="child.bz1" class="iconfont icon-star"></i>
                    <i v-if="child.bz2" class="iconfont icon-round"></i>
                    <i v-if="child.bz3" class="iconfont icon-yuanjiaoliujiaoxing"></i>
                    <i v-if="child.bz4" class="iconfont icon-plus"></i>
                  </div>
                </div>
                <div :style="{width: ulWidth + 'px'}"></div>
              </li>
            </template>
            <li v-if="secretary.length%2 === 1" class="surplus">
              <div class="branch-box"></div>
              <div :style="{'width': ulWidth + 'px'}"></div>
            </li>
          </ul>
        </template>
        <div></div>
        <template v-if="item.children && item.children.length>0">
          <branch :dataArray="item.children" :count="currentCount"></branch>
        </template>
      </li>

    </ul>
</template>

<script>
export default {
  name: 'Branch',
  props: ['dataArray', 'count'],
  data () {
    return {
      currentCount: 0
    }
  },
  computed: {
    ulWidth () {
      let width = 0
      this.dataArray.forEach((item, index) => {
        if (item.secretary) {
          item.secretary.forEach((it, sIndex) => {
            console.log(it)
            it.forEach((secretary, ssIndex) => {
              if (secretary.children) {
                console.log(secretary.children.length)
                if (width < (secretary.children.length * 194)) {
                  width = (secretary.children.length * 194)
                }
              }
            })
          })
        }
      })
      return width
    }
  },
  methods: {
    clickHandle (obj) {
      this.bus.$emit('info', obj)
    },
    isHaveChild(arr){
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].children) {
          return false
        }
      }
      return true
    }
  }
}
</script>
<style lang="scss" scoped>
  ul,li{
    list-style: none;
  }
  li.liequal{
    position: absolute;
    left: 50%;
    margin-left: 190px;
    &:before{
      content: none;
    }
  }
  li.litop{
    &:after{
      content: none;
    }
  }
  .branch-box{
    width: 172px;
    min-height: 180px;
    border: 1px solid #CCCCCC;
    border-radius: 4px;
    text-align: center;
    color: #666;
    margin-top: 20px;
    margin-left: 10px ;
    margin-right: 10px ;
    padding-bottom: 8px ;
    position: relative;
    &.equal{
      position: absolute;
      left: 50%;
      //top: calc(-100% + 20px);
      &:before{
        position: absolute;
        content: '';
        height: 1px;
        width: 112px;
        top: 50%;
        left: -114px;
        border-top: 1px dashed #000000;
      }
    }
  }
  .branch-box .branch-title{
    background-color: #2E6491;
    color: #fff;
    height: 44px;
    line-height: 44px;
    text-align: center;
    font-weight: bold;
    font-size: 14px;
  }
  .branch-box .branch-pic{
    width: 95px;
    height: 95px;
    margin: 0 auto;
    border: 1px solid #2E6491;
    margin-top: 10px;
  }
  .branch-box .branch-name{
    font-size: 14px;
    color: #2E6491;
    text-align: center;
    font-weight: bold;
    padding: 4px 10px;
  }

  ul{
    padding-left: 0px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    position: relative;
    li{
      position: relative;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding-top: 20px;
    }
  }

  ul li:only-child{
    padding-top: 0;
  }
  ul li:before{
    content: '';
    position: absolute;
    width: 50%;
    top: 20px;
    height: 20px;
    left: -0.5px;
    border-top:1px solid #000;
    border-right: 1px solid #000000;
  }
  ul li:after{
    content: '';
    position: absolute;
    width: 50%;
    top: 20px;
    height: 20px;
    left: calc(50% - 0.5px);
    border-top:1px solid #000;
    border-left: 1px solid #000000;
  }
  ul li:first-child:before,ul li:last-child:after{
    content: none;
  }
  ul:before{
    content: '';
    height: 20px;
    width: 1px;
    background-color: #000000;
    position: absolute;
    top: 0px;
  }
  ul:first-child:before{
    content: none;
  }
  ul.double{
    width: 390px;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  ul.double >li >.branch-box{
    margin-left: 0;
    margin-right: 0;
    margin-top: 40px;
  }
  ul.double:before{
    height: calc(100% - 92px);
    left: 50%;
  }
  ul.double> li{
    padding-top: 0;
  }
  ul.double>li:after,ul.double> li:before{
    width: 21px;
    height: 1px;
    top: 60%;
    border-left: 0;
    border-right: 0;
  }
  ul.double> li:nth-child(2n+1):after{
    right: -21px;
    left: auto;
  }
  ul.double >li:nth-child(2n):before{
    left: -20px;
  }
  ul.double >li:nth-child(2n+1):before,ul.double >li:nth-child(2n):after{content: none;}
  ul.double >li:nth-child(2n+1):last-child:after{
    content: '';
  }
  .iconfont{
    color: #ff0000;
  }
  .bz-box{
    width: 18px;
    right: 10px;
    top: 50px;
    position: absolute;
  }
  .bz-box i{
    float: right;
  }
  ul.level:after{
    content: '';
    position: absolute;
    width: 1px;
    height: 100%;
    background-color: #000000;
  }
  ul li.surplus{
    visibility: hidden;
  }
  ul.level li.odd:nth-last-child(2):after{
    content: none;
  }
  .org-chart ul li .line {
    width: 2px;
    height: 30px;
    background-color: #ccc;
    position: absolute;
    left: -5px;
    top: 0;
  }
</style>
