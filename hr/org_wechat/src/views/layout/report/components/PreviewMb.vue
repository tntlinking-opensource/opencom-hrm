<template>
  <component v-if="existDataView" :reportType="1" :is="reportView" :in-screen="!fullscreen"
             :show-type="'width'"
             :screen-shot="dataLoading"></component>
</template>
<script>
  import Base from './Base'
  export default {
    extends: Base,
    name: 'PreviewMb',
    components: {
    },
    data() {
      return {
        fullscreen: false,
        dataLoading: false
      }
    },
    mounted() {
    },
    methods: {

    }
  }
</script>
