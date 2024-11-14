<template>
  <RouterView></RouterView>
</template>

<script lang="ts" setup>


</script>

<style>
*{
  margin: 0;
  padding: 0;
}
/*滚动条*/
::-webkit-scrollbar {
    width: 4px;
    height: 4px;
    background-color: rgba(0, 0, 0, .2);
}

::-webkit-scrollbar-thumb {
    border-radius: 10px;
    height: 3px;
    background-color: #339AFD;
}

</style>