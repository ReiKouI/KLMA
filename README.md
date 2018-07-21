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
          <form  enctype="multipart/form-data" method="POST" action="upload-api">
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
              formData.append("categoryId", this.param1);
              formData.append("lexiconName", this.param2);
              formData.append("file", this.file);

              let config = {
                  headers: {
                      "Content-Type": "multipart/form-data"
                  }
              };
              this.$http.post("upload-api", formData, config).then(function(res) {
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
      url: "post-api",
      data: JSON.stringify(data),
      contentType: 'application/JSON',
      success: function(res) {
        /* If data post successfully */
      }
    });
  }
  ```

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
