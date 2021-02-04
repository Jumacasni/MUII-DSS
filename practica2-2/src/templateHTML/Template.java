package templateHTML;

public class Template {
	public String getHTMLTemplate() {
		return " <div class=\"container\">         \n"
				+ "	</div>\n"
				+ "	<table class=\"table table-striped table-dark\">\n"
				+ "	  	<thead>\n"
				+ "			<tr>\n"
				+ "				<th scope=\"col\">ID</th>\n"
				+ "				<th scope=\"col\">Nombre</th>\n"
				+ "				<th scope=\"col\">Descripci&oacute;n</th>\n"
				+ "				<th scope=\"col\">Precio</th>\n"
				+ "	    	</tr>\n"
				+ "		</thead>\n"
				+ "		<tbody>\n";
	}
	
	public String getHTMLTemplateCarrito() {
		return " <div class=\"container\">         \n"
				+ "	</div>\n"
				+ "	<table class=\"table table-striped table-dark\">\n"
				+ "	  	<thead>\n"
				+ "			<tr>\n"
				+ "				<th scope=\"col\">ID</th>\n"
				+ "				<th scope=\"col\">Nombre</th>\n"
				+ "				<th scope=\"col\">Cantidad</th>\n"
				+ "				<th scope=\"col\">Total</th>\n"
				+ "	    	</tr>\n"
				+ "		</thead>\n"
				+ "		<tbody>\n";
	}
	
	public Template() {
	}
}
