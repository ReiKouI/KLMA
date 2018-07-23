# KLMA

## Project problems encountered

> Prevent reload page after uploading file to server.

* Utilize *vue-resource*

  ```shell
  npm install --save vue-resource
  ```

  ```javascript
  // main.js
  ...
  import vueResource from 'vue-resource'
  Vue.use(vueResource)
  ...
  ```

* Render related component
  ```vue
  <!-- upload.vue -->
  <template>
      <div>
          <form  enctype="multipart/form-data" method="POST" action="upload-url">
              <input type="text" :value="param1">
              <input type="text" :value="param2">
              <input type="file" @change="getFile($event)">
              <button @click="onSubmit($event)">Submit</button>
          </form>
      </div>
  </template>
  <script>
  export default {
      data() {
          return {
              param1: '',
              param2: '',
              file: ''
          }
      },
      methods: {
          getFile: function(e) {
              /* Meta event object e listens the input component, 
              once submit button is clicked, 
              the chosen file will be bound to its target and then can be 
              gotten by its corresponding attributes */
              this.file = e.target.files[0];
          },
          onSubmit: function(e) {
              e.preventDefault(); // Prevent the page from reloading
              let formData = new FormData();
              formData.append("param1", this.param1);
              formData.append("param2", this.param2);
              formData.append("file", this.file);

              let config = {
                  headers: {
                      "Content-Type": "multipart/form-data"
                  }
              };
              this.$http.post("upload-url", formData, config).then(function(res) {
                  if(res.status === 200) {
                      /* If upload succeeded */
                  } else {
                      /* Once upload failed */
                  }
              });
          }
      }
  }
  </script>
  ```


> Customize JavaScript fiddler and import as tool

* Customize JavaScript

  ```javascript
  // tool.js
  export function DoSomething(param) {
    console.log(param);
  }
  ```

* Import as tool

  ```vue
  <template>
  	...
  </template>
  <script>
  import { DoSomething } from "url/to/tool.js"
    export default {
      methods: {
        otherFunction: function() {
          DoSomething("Hello");
        }
      }
    }
  </script>
  ```

> Ajax post data

* Ensemble data and utilize JSON.stringfy()

  ```javascript
  ...
  postData: function() {
    let data = {};
    data['param1'] = "param1";
    data['param2'] = 2;
    $.post({
      url: "post-url",
      data: JSON.stringify(data),
      contentType: 'application/JSON',
      success: function(res) {
        /* If data post successfully */
      }
    });
  }
  ```


> Sticky footer

* Utilization of `calc` tag ( which is not *elegant* )

  ```vue
  <template>
    <div id="app">
      <div class="header">This is header</div>
      <div class="content">This is content</div>
      <div class="footer">This is footer</div>
    </div>
  </template>
  <style lang="stylus">
  #app
    .content
      min-height calc(100vh - 50px)
    .footer
      height 50px
  </style>
  ```


> Shared state with vuex

* Install vuex

  ```shell
  npm install --save vuex
  ```

* Import vuex

  ```javascript
  // store/index.js
  import Vue from 'vue'
  import vuex from 'vuex'

  Vue.use(vuex)

  const state = {
      userInfo: {
          userId: '',
          nickName: '',
          planId: ''
      },
      hasLoggedIn: false
  }

  const mutations = {
    	// be carefully to use the parameter as only one object parameter can be passed
      saveUserInfo(state, data) {
          state.userInfo.userId = data.id;
          state.userInfo.nickName = data.nickname;
          state.userInfo.planId = data.planId;
      },
      setLoginState(state, data) {
          state.hasLoggedIn = data;
      }
  }

  export default new vuex.Store({
      state, 
      mutations,
  })
  ```

* Register vuex store

  ```javascript
  // main.js
  ...
  import store from './store'
  ...
  new Vue({
    router,
    store,
    render: h => h(App)
  }).$mount('#app')
  ```

* Update state

  ```vue
  <template>
  	...
  </template>
  <script>
  import { mapMutations, mapState } from 'vuex'
  export default {
    computed: mapState({
      userInfo: state => state.userInfo  
    }),
    methods: {
      // Similiar to reflection in Java
      ...mapMutations([
        'saveUserInfo',
        'setLoginState'
      ]),
      login: function() {
          let data = {};
          let _this = this;	// Notice pointers "this" points to different objects
          $.post({
            url: 'api-url',
            data: JSON.stringify(data),
            contentType: 'application/JSON',
            xhrFields: { withCredentials: true },		// Post with cookies
            success: function(result) {
  			_this.saveUserInfo(result.data);
              _this.setLoginState(true);
            }
          })
      }
    }
  }
  </script>
  ```

  ​

  ​

## Project setup

```shell
npm install
```

### Compiles and hot-reloads for development
```shell
npm run serve
```

### Compiles and minifies for production
```shell
npm run build
```
