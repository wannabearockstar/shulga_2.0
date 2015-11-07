var utils = {};

(function ($) {

    'use strict';

    utils.template = (function () {

        var cache = {};
        var tmpl_dir = '/js/templates/';

        function init(tmpl_name) {
            var deferred = $.Deferred();

            if (cache[tmpl_name]) {
                return deferred.resolve().promise();
            }

            $.ajax({

                url: getUrl(tmpl_name),
                method: 'GET',
                async: true

            }).then(function(tmpl){
                cache[tmpl_name] = _.template(tmpl);
                deferred.resolve();
            });

            return deferred.promise();
        }

        function render(tmpl_name, tmpl_data) {
            return cache[tmpl_name](tmpl_data);
        }

        function getUrl(tmpl_name) {
            return tmpl_dir + tmpl_name + '.html';
        }

        return {
            init: init,
            render: render
        };
    })();

    utils.entity = (function () {
        var cache = {};
        var entities_dir = '/js/entities/';

        function init(entity_name) {
            var deferred = $.Deferred();

            if (cache[entity_name]) {
                return deferred.resolve().promise();
            }

            $.getJSON(getUrl(entity_name), function(entities){
                cache[entity_name] = entities;
                deferred.resolve();
            });

            function getUrl(entity_name) {
                return entities_dir + entity_name + '.json';
            }

            return deferred.promise();
        }

        function list(entity_name) {
            return cache[entity_name];
        }

        return {
            init: init,
            list: list
        };
    })();

})(jQuery);


