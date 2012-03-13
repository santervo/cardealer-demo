$(function() {

	var validLicenceNumber = /^[A-Za-zöäåÖÄÅ]{1,3}-\d{1,3}$/;

	var validNumber = /^-?\d+(\.\d+)?$/

	window.Car = Backbone.Model.extend({
		idAttribute: "_id",

		urlRoot: "/cars",
		
		//TODO: validate on server side
		validate: function(attrs) {
			this.invalid = [];

			if (!attrs.model) this.invalid.push("model");
			if (!validLicenceNumber.test(attrs.licenceNumber)) this.invalid.push("licenceNumber");
			if (!validNumber.test(attrs.price)) this.invalid.push("price");

			return this.invalid.length > 0 ? this.invalid : undefined;
		},
	});

	window.CarsCollection = Backbone.Collection.extend({
		model: Car,

		url: "/cars",

	});

	window.CarsTableView = Backbone.View.extend({
		el: $("#cars-table"),

		initialize: function() {
			this.collection.on("reset", this.resetView, this);
			this.collection.on("add", this.addCar, this);
		},

		resetView: function() {
			this.collection.each(this.addCar, this);
		},

		addCar: function(car) {
			this.$el.dataTable().fnAddData(
				[car.get("model"),car.get("licenceNumber"),car.get("price")]);
		},

		render: function() {
			this.$el.dataTable({sDom: "ftlp"});
		}
	});

	var carFormTemplate = _.template($("#car-form-template").html());

	window.CarFormDialogView = Backbone.View.extend({
		id: "car-form-dialog",

		initialize: function() {
			this.dialogOpts = {
				modal: true,
				title: this.options.title,
				width: 300,
				close: $.proxy(this, "close"),
				buttons: {
					"Tallenna": $.proxy(this, "saveButtonPushed")
				}
			};
		},

		saveButtonPushed: function() {
			var attrs = {
				model: $("#model").val(),
				licenceNumber: $("#licenceNumber").val(),
				price: $("#price").val()
			};
			var opts = {
				success: $.proxy(this, "success"),
				error: $.proxy(this, "failed")
			};
			this.model.save(attrs, opts);
		},

		success: function() {
			this.collection.add(this.model);
			this.close();
		},

		failed: function(car, failedFields) {
			var fields = ["model", "licenceNumber", "price"];
			_.chain(fields).difference(failedFields).each(this.removeErrorClass, this).value();
			_(failedFields).each(this.addErrorClass, this);
		},

		addErrorClass: function(fieldId) {
			var field = $("#" + fieldId, this.$el);
			field.addClass("error");
		},

		removeErrorClass: function(fieldId) {
			var field = $("#" + fieldId, this.$el);
			field.removeClass("error");
		},

		render: function() {
			$(document.body).append(this.el);
			this.$el.html(carFormTemplate());
			this.$el.dialog(this.dialogOpts);
		},

		close: function() {
			this.$el.dialog("destroy");
			this.remove();
		},
	});

	window.CarsView = Backbone.View.extend({
		el: $("#cars-view"),

		initialize: function() {
			this.carsCollection = new CarsCollection;
			this.carsTableView = new CarsTableView({collection: this.carsCollection});
		},

		render: function() {
			this.carsTableView.render();
			this.carsCollection.fetch();
			$("#add-car-link").click($.proxy(this,"showAddCarForm"));
		},

		showAddCarForm: function() {
			var view = new CarFormDialogView({
				model: new Car,
				collection: this.carsCollection,
				title: "Add new car"
			});
			view.render();
		}
	});

	window.CarDealerAppView = Backbone.View.extend({
		el: $("#app-view"),

		initialize: function() {
			this.carsView = new CarsView;
		},

		render: function() {
			this.carsView.render();
		}
	});

});
