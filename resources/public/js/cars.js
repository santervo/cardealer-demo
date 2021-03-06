$(function() {

	window.Car = Backbone.Model.extend({
		idAttribute: "_id",

		urlRoot: "/cars",
	});

	window.CarsCollection = Backbone.Collection.extend({
		model: Car,

		url: "/cars",

	});

	window.CarsTableItemView = Backbone.View.extend({
		render: function() {
			var idx = this.options.dataTable.fnAddData([
				this.model.get("model"), 
				this.model.get("licenceNumber"),
				this.model.get("price"),
				'<a class="make-deal-link action" href="#">Make a deal</a>']);
			var row = this.options.dataTable.fnGetNodes(idx);
			$("a.make-deal-link", row).click($.proxy(this, "makeDeal"));
		},

		makeDeal: function() {
			// implement
		}
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
			var itemView = new CarsTableItemView({
				model: car,
				dataTable: this.$el.dataTable()
			});
			itemView.render();
		},

		render: function() {
			this.$el.dataTable({sDom: "ftlp", aoColumnDefs: [ 
			      { sWidth: "90px", "aTargets": [ 3 ] }
			    ]});
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
					"Tallenna": $.proxy(this, "saveCar")
				}
			};
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

		saveCar: function() {
			var attrs = {
				model: $("#model").val(),
				licenceNumber: $("#licenceNumber").val(),
				price: $("#price").val()
			};
			var opts = {
				success: $.proxy(this, "savingSucceeded"),
				error: $.proxy(this, "savingFailed")
			};
			this.model.save(attrs, opts);
		},

		savingSucceeded: function(model, response) {
			this.collection.add(this.model);
			this.close();
		},

		savingFailed: function(model, response) {
			var errorMap = JSON.parse(response.responseText);
			this.setFormErrors(_.keys(errorMap));
		},

		setFormErrors: function(failedFields) {
			$("input", this.$el).removeClass("error");
			_(failedFields).each(function(id) {
				$("#"+id).addClass("error");
			});
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

	window.DealsTableView = Backbone.View.extend({
		el: $("#deals-table"),

		render: function() {
			this.$el.dataTable({sDom: "ftlp"});
		}
	}),

	window.DealsView = Backbone.View.extend({
		el: $("#deals-view"),

		initialize: function() {
			this.dealsTableView = new DealsTableView;
		},

		render: function() {
			this.dealsTableView.render();
		}
	}),

	window.CarDealerAppView = Backbone.View.extend({
		el: $("#app-view"),

		initialize: function() {
			this.carsView = new CarsView;
			this.dealsView = new DealsView;
		},

		render: function() {
			this.$el.tabs();
			this.carsView.render();
			this.dealsView.render();
		}
	});

});
