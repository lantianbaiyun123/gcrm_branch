var bird = require('gulp-bird');

var birdConfig = {
      "servers": {
        "8877": {
          "basePath": "D:/Projects/gcrm/code/src/main/webapp/front"
        }
      },
      "transRules": {
        "8877": {
          "targetServer": {
            "host": "10.46.133.242",
            "port": "8101"
          },
          "regExpPath": {
            "gcrm/": {
              "path": "gcrm/"
            }
          }
        },
        "ajaxOnly": false
      }
    };



  bird.start(birdConfig.servers, birdConfig.transRules);
