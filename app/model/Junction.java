package model;

public enum Junction {
	OR{
		@Override
		public String toString(){
			return "OR";
		}
	},
	AND{
		@Override
		public String toString(){
			return "AND";
		}
	}
}