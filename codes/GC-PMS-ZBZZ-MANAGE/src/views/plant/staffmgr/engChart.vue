<template>
  <div>
    <div id="engChart" :style="{width:width,height:height}"></div>
  </div>
</template>

<script>
  import { engData } from "@/api/plant/staffmgr";
  export default {
    props: {
      width: {
        type: String,
        default: '100%'
      },
      height: {
        type: String,
        default: '200px'
      },
    },
    data() {
      return {
        option: {
          title: {
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            orient: 'vertical',
            left: 'left',
            data: []
          },
          toolbox: {
            show: true,
            feature: {
              mark: {show: true},
              magicType: {
                show: true,
                type: ['pie', 'funnel']
              },
              restore: {show: true},
              saveAsImage: {show: true}
            }
          },
          series: [
            {
              label: {
                formatter: '{b}: ({d}%)'
              },
              name: 学历,
              type: 'pie',
              radius: ['50%', '70%'],
              selectedMode: 'single',
              data: [
                {value: 335, name: 直接访问},
              ],
              emphasis: {
                label: {
                  show: true,
                  fontSize: '21',
                  fontWeight: 'bold'
                }
              },
            }
          ]
        },
        // 查询参数
        queryParams: {
          item: 1,
        },
        chart: null,
        engData : []
      }
    },
    mounted() {
      this.$nextTick(() => {
        engData(this.queryParams).then(response => {
          this.engData = response
          for(let i = 0 ; i <this.engData.length ; i++){
            this.option.legend.data[i] = this.engData[i].dataName
              this.option.series[0].data[i] = {value:this.engData[i].dataNum , name: this.engData[i].dataName}
          }
          this.initChart()
        });
      })
    },
    methods: {
      /** 获取当前年份 */
      getNowTime() {
        var now = new Date();
      },
      initChart() {
        // 基于准备好的dom，初始化echarts实例
        this.chart = this.echarts.init(document.getElementById('engChart'))
        this.chart.setOption(this.option)
      }
    }
  }
</script>
