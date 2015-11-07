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

        function init(entity_name, entities_dir, entity_ext) {
            var deferred = $.Deferred();

            entity_ext = entity_ext || '.json';
            entities_dir = entities_dir || '/js/entities/';

            if (cache[entity_name]) {
                return deferred.resolve().promise();
            }

            $.getJSON(entities_dir + entity_name + entity_ext, function(entities){
                cache[entity_name] = entities;
                deferred.resolve();
            });

            return deferred.promise();
        }

        function list(entity_name) {
            return cache[entity_name];
        }

        function bind(entity_name, param_name, val) {
            var entities = utils.entity.list(entity_name);
            return _.find(entities, function (item) {
                return item[param_name] = val;
            });
        }

        return {
            init: init,
            list: list,
            bind: bind
        };
    })();

})(jQuery);


