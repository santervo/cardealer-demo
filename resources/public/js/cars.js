$(function() {

	window.Car = Backbone.Model.extend({
		idAttribute: "_id",
		urlRoot: "/cars"
	});

	window.CarsCollection = Backbone.Collection.extend({
		model: Car,
		url: "/cars"
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
			this.model.save();
			this.collection.add(this.model);
			this.close();
		},

		render: function() {
			$(document.body).append(this.el);
			this.$el.html(carFormTemplate());
			this.$el.dialog(this.dialogOpts);
			Backbone.ModelBinding.bind(this);
		},

		close: function() {
			Backbone.ModelBinding.unbind(this);
			this.$el.dialog("destroy");
			this.remove();
		}
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
});
