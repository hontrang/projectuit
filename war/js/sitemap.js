$(document).ready(function(e) {
    $('#doSearchSite').click(function(e) {
        var site = rel_to_abs($('#searchSite').val());
        var index = 0;
        var worker = new Worker('worker.js');
        var array = [site];
        //
        $(".tran").append("<a href='" + site + "' class='unload linkInt'>" + site + "</a><br>");
        worker.postMessage({site: site});
        function message(site) {
            worker.postMessage({site: site});
        }
        worker.addEventListener('message', function(e) {
            // $(".tran").text(JSON.stringify(e.data));
            var data = e.data;
            var json = JSON.parse(data);
            if ($.isEmptyObject(json)) {
                // console.log('error');
            } else {
                show(json);
            }
            function show(json) {
                var arrayInt = json.LinkInt;
                for (var i = 0; i < arrayInt.length; i++) {
                    if (array.indexOf(arrayInt[i].toString()) == -1 || arrayInt[i].toString().indexOf("#") == -1) {
                       // alert("ok")
                        $(".tran").append("<a href='" + arrayInt[i].toString() + "' class='unload linkInt'>" + arrayInt[i].toString() + "</a><br>");
                        array.push(arrayInt[i].toString());
                    }
                }
            }
            setTimeout(function() {
                index = index + 1;
                var site = $('.linkInt:eq(' + index + ')').text();
                if(site ==''){
                    worker.terminate();
                    alert("success");
                }else{
                    //message(site);
                }
            }, 1000);
        }, false);


        $('#stop_button').click(function(e) {
            worker.terminate();
            console.log("Terminate web Worker");
        });
    });
    function rel_to_abs(url) {
        if (url.indexOf("http://") === 0 || url.indexOf("https://") === 0) {
            return url;
        }
        else {
            url = "http://" + url;
            return url;
        }
    }

});