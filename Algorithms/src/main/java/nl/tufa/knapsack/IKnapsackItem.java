package nl.tufa.knapsack;

/*
	Copyright 2020 M.C.Slooff
	
	This file is part of 'Algorithms'
	
	'Algorithms' is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	'Algorithms' is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with 'Algorithms'.  If not, see <https://www.gnu.org/licenses/>.
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.	
*/

public interface IKnapsackItem {
	
	public String getTag();
	public void setTag(String tag);
	
	public Double getValue();
	public void setValue(Double value);
	
	public Double getSize();
	public void setSize(Double size);
	
}
