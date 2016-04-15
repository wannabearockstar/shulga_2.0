var utils = {};

(function ($) {

	'use strict';

	utils.template = new (function () {
		var cache = {};
		var self = this;

		self.init = function (tmpl_name, tmpl_dir, tmpl_ext) {
			var deferred = $.Deferred();

			tmpl_ext = tmpl_ext || '.html';
			tmpl_dir = tmpl_dir || '/js/templates/';

			if (cache[tmpl_name]) {
				return deferred.resolve().promise();
			}

			$.ajax({

				url: tmpl_dir + tmpl_name + tmpl_ext,
				method: 'GET',
				async: true

			}).then(function (tmpl) {
				cache[tmpl_name] = _.template(tmpl);
				deferred.resolve();
			});

			return deferred.promise();
		};

		self.render = function (tmpl_name, tmpl_data) {
			return cache[tmpl_name](tmpl_data);
		};
	})();

	utils.entity = new (function () {
		var cache = {};
		var self = this;

		self.init = function (entity_name, entities_dir, entity_ext) {
			var deferred = $.Deferred();

			entity_ext = entity_ext || '.json';
			entities_dir = entities_dir || '/js/entities/';

			if (cache[entity_name]) {
				return deferred.resolve().promise();
			}

			$.getJSON(entities_dir + entity_name + entity_ext, function (entities) {
				cache[entity_name] = entities;
				deferred.resolve();
			});

			return deferred.promise();
		};

		self.initFromController = function (entity_name, controller_url) {
			var deferred = $.Deferred();

			if (cache[entity_name]) {
				return deferred.resolve().promise();
			}

			$.getJSON(controller_url, function (resp) {
				cache[entity_name] = resp.data;
				deferred.resolve();
			});

			return deferred.promise();
		};

		self.initFromExisting = function (entity_name, src) {
			cache[entity_name] = src
		};

		self.list = function (entity_name) {
			return cache[entity_name];
		};

		self.bind = function (entity_name, param_name, val) {
			var entities = utils.entity.list(entity_name);
			return _.find(entities, function (item) {
				return item[param_name] == val;
			});
		};
	})();

})(jQuery);


