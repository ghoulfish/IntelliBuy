/**
 * Split table:
 * Keep 1st row.
 * Convert style.display none<-->table-row
 * @author Chenxi Zhang
 */

define([], function(){
	"use strict";
	
	var table = {};
	
	var pageSize = 20;
	var tableLength;
	var lastPage;
	
	table.setPageSize = function setSize(size){
		pageSize = size;
		if (tableLength != null){
			setLastPage();
		}
	}
	
	table.setLast = function setLastPage(){
		lastPage = Math.ceil( tableLength/pageSize );
		return lastPage;
	}
	
	table.setTableLength = setLength;
	
	function setLastPage() {
		lastPage = Math.ceil( tableLength/pageSize );
	}
	
	function setLength(len){
		tableLength = len;
		setLastPage();
	}
	
	table.toPage = function go(page, table){
		if (tableLength == null) {
			setLength(table.rows.length);
		}
		if (page < 1 || page > lastPage){
			return false;
		}
		var first = (page-1) * pageSize + 1;
		var last = page * pageSize;
		for (var i = 1; i < table.rows.length; i++){
			table.rows[i].style.display = "none";
			if (i >= first && i <= last ){
				table.rows[i].style.display = "table-row";
			}
		}
		return true;
	}
	
	return table;
})