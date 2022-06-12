public void deleteLast(){
        while(this.front.getNext()!=null){
            this.front = this.front.getNext();
        } 
        this.front=null;
    }