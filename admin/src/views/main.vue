<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header-view></the-header-view>

    <a-layout>
      <the-sider-view></the-sider-view>
      <a-layout style="padding: 0 24px 24px">
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
import {defineComponent, ref} from 'vue';
import TheHeaderView from "@/components/the-header";
import TheSiderView from "@/components/the-sider";

export default defineComponent({
  name: "login-view",
  components: {TheSiderView, TheHeaderView},

  setup() {
    const count = ref(0);
    // axios.get("/member/member/count").then((response) => {
    //   let data = response.data;
    //   if (data.success) {
    //     count.value = data.content;
    //   } else {
    //     notification.error({description: data.message});
    //   }
    // });
    return {
      count
    };
  },
});
</script>
<style scoped>
</style>