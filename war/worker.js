self.addEventListener('message', function(e) {
    var data = e.data;
    if (data.site != '') {
        var url = "GetHtml?val=" + data.site;
        //console.log(url);
        load(url, function(data) {
            self.postMessage(data.responseText);
            //self.close();
        });
    } else {
      //  self.close();
      self.postMessage({terminate:"terminate"});
    }
}, false);
function load(url, callback) {
    var xhr;

    if (typeof XMLHttpRequest !== 'undefined')
        xhr = new XMLHttpRequest();
    else {
        var versions = ["MSXML2.XmlHttp.5.0",
            "MSXML2.XmlHttp.4.0",
            "MSXML2.XmlHttp.3.0",
            "MSXML2.XmlHttp.2.0",
            "Microsoft.XmlHttp"]

        for (var i = 0, len = versions.length; i < len; i++) {
            try {
                xhr = new ActiveXObject(versions[i]);
                break;
            }
            catch (e) {
            }
        } // end for
    }

    xhr.onreadystatechange = ensureReadiness;

    function ensureReadiness() {
        if (xhr.readyState < 4) {
            return;
        }

        if (xhr.status !== 200) {
            return;
        }
        if (xhr.status == 500) {
            self.postMessage("error");
            alert("error");
        }
        // all is well	
        if (xhr.readyState === 4) {
            callback(xhr);
        } else {
            self.postMessage("error");
        }
    }

    xhr.open('GET', url, true);
    xhr.send('');
}

