package ua.np.services.smsinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2014 Nova Poshta. All rights reserved.
 * http://novaposhta.ua/
 * <p/>
 * for internal use only!
 * <p/>
 * User: yushchenko.i
 * email: yushchenko.i@novaposhta.ua
 * Date: 11.02.14
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "messages")
public class UpdateRequest {

    private List<UpdateItem> itemList;

    public UpdateRequest() {
    }

    public UpdateRequest( List<UpdateItem> itemList ) {
        if( this.itemList == null ){
            this.itemList = new ArrayList<>(  );
        }
        this.itemList.addAll( itemList );
    }

    public void addUpdateItem(UpdateItem updateItem){
        if( this.itemList == null ){
            this.itemList = new ArrayList<>(  );
        }
        this.itemList.add( updateItem );
    }

    public List<UpdateItem> getItemList() {
        if( this.itemList == null ){
            this.itemList = new ArrayList<>(  );
        }
        return itemList;
    }

    public void setItemList( List<UpdateItem> itemList ) {
        this.itemList = itemList;
    }
}
