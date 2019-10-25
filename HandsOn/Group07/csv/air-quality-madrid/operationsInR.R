# load the original dataset, this will be used only to make visual comparisons during the flow of transformations
originalDataset <- read.csv(file="raw-data/merged_dataset.csv", header = T, sep = ",", stringsAsFactors = F)

# load again the dataset, this will be modified
workingDataset <- read.csv(file="raw-data/merged_dataset.csv", header = TRUE, sep=",", stringsAsFactors = F)

# load the needed library
library(reshape)

# filter the data on the first 20 days of the first month of the year to reduce the dataset and make to whole project smoother
workingDataset <- subset(workingDataset, 
                         format(as.Date(date), "%m") == "01" & format(as.Date(date), "%d") < 20)

# melt the columns about the pollutants into a new column, in order to have a single column for the pollutant
workingDataset <- melt(workingDataset, measure.vars=c("BEN", "CH4", "CO", "EBE", "NMHC", "NO",
                          "NO_2", "NOx", "O_3", "PM10", "PM25", "SO_2", "TCH", "TOL"))

# rename the default name of the new created column
names(workingDataset)[names(workingDataset) == "variable"] <- "pollutant"

# remove all the measurements with a null value
workingDataset <- subset(workingDataset, !is.na(value))

# create a new column with the full name of the pollutant to make reconciliation easier
workingDataset$pollutant_name <- workingDataset$pollutant
workingDataset$pollutant_name <- as.character(workingDataset$pollutant_name)
workingDataset$pollutant_name[workingDataset$pollutant_name == "BEN"] <- "Benzene"
workingDataset$pollutant_name[workingDataset$pollutant_name == "CH4"] <- "Metane"
workingDataset$pollutant_name[workingDataset$pollutant_name == "CO"] <- "Carbon Monoxide"
workingDataset$pollutant_name[workingDataset$pollutant_name == "EBE"] <- "Ethylbenzene"
workingDataset$pollutant_name[workingDataset$pollutant_name == "NMHC"] <- "Volatile Organic Compound"
workingDataset$pollutant_name[workingDataset$pollutant_name == "NO"] <- "Nitric Oxide"
workingDataset$pollutant_name[workingDataset$pollutant_name == "NO_2"] <- "Nitrogen Dioxide"
workingDataset$pollutant_name[workingDataset$pollutant_name == "NOx"] <- "Nitrous Oxide"
workingDataset$pollutant_name[workingDataset$pollutant_name == "O_3"] <- "Ozone"
workingDataset$pollutant_name[workingDataset$pollutant_name == "PM10"] <- "PM 10 (excluding PM 2.5)"
workingDataset$pollutant_name[workingDataset$pollutant_name == "PM25"] <- "PM 2.5"
workingDataset$pollutant_name[workingDataset$pollutant_name == "SO_2"] <- "Sulfur Dioxide"
workingDataset$pollutant_name[workingDataset$pollutant_name == "TCH"] <- "Hydrocarbon"
workingDataset$pollutant_name[workingDataset$pollutant_name == "TOL"] <- "Toluene"

# create a new column as id for the measurements and move it to the left
workingDataset$measurement_id <- paste("Measurement", 1:nrow(workingDataset), sep="_")
workingDataset <- workingDataset[, c(11, 1:8, 10, 9)]

# create a new column with the name of the neighbourhood related to the address, to facilitate reconciliation for the addresses
workingDataset$neighbourhood_name <- workingDataset$station_name
workingDataset$neighbourhood_name <- as.character(workingDataset$neighbourhood_name)
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Arturo Soria"] <- "Colina"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Avda. Ramón y Cajal"] <- "Hispanoamerica"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Barajas Pueblo"] <- "Casco_de_Barajas"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Barrio del Pilar"] <- "Pilar"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Casa de Campo"] <- "Case_de_Campo"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Castellana"] <- "Castellana"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Cuatro Caminos"] <- "Cuatro_Caminos"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "El Pardo"] <- "Pardo"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Ensanche de Vallecas"] <- "Vallecas"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Escuelas Aguirre"] <- "Recoletas"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Farolillo"] <- "Isidro"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Juan Carlos I"] <- "Corralejos"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Mendez Alvaro"] <- "Atocha"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Moratalaz"] <- "Vinateros"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Parque del Retiro"] <- "Jeronimos"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Plaza Castilla"] <- "Castilla"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Pza. de España"] <- "Arguelles"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Pza. del Carmen"] <- "Sol"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Pza. Fernández Ladreda"] <- "Abrantes"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Sanchinarro"] <- "Valdefuentes"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Tres Olivos"] <- "Valverde"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Vallecas"] <- "Vallecas"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Urb. Embajada"] <- "Alameda_de_Osuna"
workingDataset$neighbourhood_name[workingDataset$neighbourhood_name == "Villaverde"] <- "Villaverde"

workingDataset <- workingDataset[, c(1:5, 12, 6:11)]

# export the new dataset
write.csv(workingDataset, file = "final_dataset.csv")



