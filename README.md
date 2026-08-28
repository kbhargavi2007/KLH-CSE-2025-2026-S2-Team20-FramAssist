**Team Lead: Bhargavi**
**Guide: Dr. Vinay Kumar**
# Creating the Project Folder in VS Code

The first step in developing the **Farm Assist** project is to create and organize the project folder in **Visual Studio Code**. A proper folder structure helps keep the source code, documents, and different modules of the project organized.

## Step 1: Create the Main Project Folder

1. Open **Visual Studio Code**.
2. Go to **File → Open Folder**.
3. Create a new folder named:

```text
Farm Assist
```

4. Open the folder in VS Code.
5. The **Farm Assist** folder will now appear in the Explorer panel.

## Step 2: Create the Corpus Folder

Inside the `Farm Assist` folder, create a new folder named:

```text
corpus
```

The `corpus` folder is used to store the documents containing agricultural information that will be searched by the application.

Add the required `.docx` files inside this folder.

Example:

```text
corpus/
├── Agriculture.docx
├── Crops.docx
├── Fertilizers.docx
└── PestControl.docx
```

## Step 3: Create the Required Java Folders and Files

Next, create the folders and Java files required for the project.

Create:

```text
CorpusReader/
FileSearcher/
ZAlgorithm.java
Main.java
```

The `CorpusReader` folder contains the files responsible for reading the documents, while `FileSearcher` handles the searching functionality. `ZAlgorithm.java` contains the Z Algorithm implementation used for pattern matching, and `Main.java` is used to run the application.

## Final Folder Structure

The final structure of the project in VS Code will look like:

```text
Farm Assist/
│
├── corpus/
│   ├── Agriculture.docx
│   ├── Crops.docx
│   ├── Fertilizers.docx
│   └── PestControl.docx
│
├── CorpusReader/
│   └── CorpusReader.java
│
├── FileSearcher/
│   └── FileSearcher.java
│
├── ZAlgorithm.java
├── Main.java
└── README.md
```

This structure keeps the project **organized, modular, and easy to maintain**, with documents and source-code components separated according to their purpose.
