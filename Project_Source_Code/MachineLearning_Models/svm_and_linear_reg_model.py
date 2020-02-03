# -*- coding: utf-8 -*-
"""
Created on Thu Jan  2 16:54:06 2020

@author: I521676
"""

from flask import Flask, request
import pymongo
import pandas as pd
import pickle
from bson.objectid import ObjectId
from sklearn import  datasets, linear_model, metrics 


app = Flask(__name__)

@app.route("/")
def hello():
    
    args = request.args
    
    collectionName = args['collectionName']
    columnName = args['columnName']
    
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["ReverseEngineering"]
    query = {'isWrecked': False}
    mycol = mydb[collectionName].find(query).limit(100)
    
    df = pd.DataFrame(list(mycol))
    df.drop('_id',axis=1,inplace=True)
    
    
    multiColStats= ''
    colDatatype = ''
    y_pred= ''
    accuracy=''
    
    query1 = {'fileName': 'dataset_1'}  
    mycoll = mydb['dataProfilerInfo'].find(query1)
    print(mycoll)
    for x in mycoll:
        for data in x['datasetStats']:
            if data.get('columnName') == columnName :
                multiColStats = data.get('profilingInfo').get('columnStats').get('multiColumnStats')
                colDatatype = data.get('profilingInfo').get('columnDataType')
                print( multiColStats.get('dependantColumnNames'))
                print(colDatatype)
    
    # Importing the dataset
    if colDatatype == 'Boolean':
        df[columnName] = (df[columnName] == 'TRUE').astype(int)
        
    columns = multiColStats.get('dependantColumnNames')
    X = df.loc[:,  columns].values
    y = df.loc[:, columnName].values

    
    # Splitting the dataset into the Training set and Test set
    from sklearn.model_selection import train_test_split
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.25, random_state = 0)
  
    classifier = ''
    
    if colDatatype == 'Integer' or colDatatype == 'Decimal':
        print('Calling linear regression')
        classifier = linear_model.LinearRegression()
        classifier.fit(X_train, y_train)
        # Predicting the Test set results
        y_pred = classifier.predict(X_test)
        accuracy = metrics.r2_score(y_test,y_pred)
    elif colDatatype == 'Boolean':
        # Fitting SVM to the Training set
        print('calling SVM')
        from sklearn.svm import SVC
        classifier = SVC(kernel = 'linear', random_state = 0)
        classifier.fit(X_train, y_train)
        # Predicting the Test set results
        y_pred = classifier.predict(X_test)
        accuracy = metrics.accuracy_score(y_test,y_pred)


    # Predicting the Test set results
    y_pred = classifier.predict(X_test)


    if accuracy > 0:
        if accuracy <= 1:
            print(accuracy)
            filename = 'svm_model.sav'
            pickle.dump(classifier, open(filename, 'wb'))
            print ("model saved")
        return "Success"
    
    else:
        print("wrong")  
        print(accuracy)  
    return "Failure"
    

@app.route("/model")
def ml_model():
    mlModel = pickle.load(open("svm_model.sav","rb"))
    
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["ReverseEngineering"]
    
    args = request.args
    multiColStats=''
    
    collectionName = args['collectionName']
    columnName = args['columnName']
    oid= args['oid']
    query = {"_id" : ObjectId(oid)}
    print(oid)
    print(collectionName)
    
    mycol = mydb[collectionName].find(query)
   
    df = pd.DataFrame(list(mycol))
    
    query1 = {'fileName': 'dataset_1'}  
    mycoll = mydb['dataProfilerInfo'].find(query1)
    print(mycoll)
    for x in mycoll:
        for data in x['datasetStats']:
            if data.get('columnName') == columnName :
                multiColStats = data.get('profilingInfo').get('columnStats').get('multiColumnStats')
                colDatatype = data.get('profilingInfo').get('columnDataType')
                print( multiColStats.get('dependantColumnNames'))
                print(colDatatype)
    
    columns = multiColStats.get('dependantColumnNames')
    test_data = df.loc[:, columns].values
    
    prediction = mlModel.predict(test_data)
    print(prediction[0])
    return str(prediction[0])

if __name__ == '_main_':
    app.run(debug=False)