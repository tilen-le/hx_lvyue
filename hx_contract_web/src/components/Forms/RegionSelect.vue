<!-- 省市区三级联动选择器 -->
<template>
  <el-cascader
    v-model="thisValue"
    :options="options"
    :props="defultProp"
    :placeholder="placeholder"
    :disabled="disabled"
    :clearable="clearable"
    show-all-levels
    filterable
    @change="handleChange"/>
</template>

<script>
  import city from "./city";

  export default {
    name: 'RegionSelect',
    props: {
      // 默认值，
      // 可传递数组，例如：['江苏省','南通市','崇川区']
      // 也可传递字符串，例如：江苏省,南通市,崇川区
      value: {
        type: String | Array,
        default: undefined
      },
      placeholder: {
        type: String,
        default: "请选择"
      },
      disabled: {
        type: Boolean,
        default: false
      },
      clearable: {
        type: Boolean,
        default: true
      }
    },

    data() {
      return {
        options: city,
        defultProp: {
          label: "name",
          value: "name",
          children: "cityList"
        },
      }
    },

    computed: {
      thisValue: {
        get() {
          if(this.$v.isString(this.value)) {
            return this.value.split(',');
          }
          return this.value
        },
        set(val) {
          const value = val && val.length > 0 ? val.join(",") : "";
          this.$emit('input', value);
        }
      },
    },

    methods: {

      handleChange(value) {
        this.$emit("change", value);
      }

    }
  }
</script>

<style lang="scss" scoped>

</style>
