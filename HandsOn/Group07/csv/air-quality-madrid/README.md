
### Dataset structure
The original dataset is made up by different csv files. They are available in the *raw-data* folder. The csv file *madrid_2018.csv* contains data about the measurement collected during the year 2018 by different sensors placed in stations that are described in the file *stations.csv*.

In the same folder you can find an intermediate version of the dataset that is the merge of *stations.csv* and *madrid_2018.csv*, called *merged_dataset.csv*. 

The *final_dataset.csv* file is the one we used to perform the tasks. It has been obtained by applying some operations in R, reported and commented in the *operationsInR.R* file.

### Dataset description

This dataset defines stations as the higher hierarchical level. Not every station has the same equipment, therefore each station can measure only a certain subset of particles. The complete list of possible pollutants and their explanations are:

* **SO_2**: sulphur dioxide level measured in μg/m³. 
* **CO**: carbon monoxide level measured in mg/m³.
* **NO**: nitric oxide level measured in μg/m³. 
* **NO_2**: nitrogen dioxide level measured in μg/m³.
* **PM25**: particles smaller than 2.5 μm level measured in μg/m³.
* **PM10**: particles smaller than 10 μm. 
* **NOx**: nitrous oxides level measured in μg/m³. 
* **O_3**: ozone level measured in μg/m³. 
* **TOL**: toluene (methylbenzene) level measured in μg/m³. 
* **BEN**: benzene level measured in μg/m³.
* **EBE**: ethylbenzene level measured in μg/m³. 
* **TCH**: total hydrocarbons level measured in mg/m³. 
* **CH4**: methane level measured in mg/m³. 
* **NMHC**: non-methane hydrocarbons (volatile organic compounds) level measured in mg/m³. 

### Source
https://www.kaggle.com/decide-soluciones/air-quality-madrid/
