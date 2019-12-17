Welcome to the spring-data-jpa-guide wiki!

https://vladmihalcea.com/the-best-way-to-lazy-load-entity-attributes-using-jpa-and-hibernate/
````

Hello,
I am working with Wildfly 11 which uses hibernate 5.1.10
Lazy loading doesn’t work for me, it always makes eager loading. I am doing following:
@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "device")
@LazyToOne(LazyToOneOption.NO_PROXY)
public DeviceRegistrationEntity getDeviceRegistration() {
	return deviceRegistration;
}

In Wildfly 8.2.0 (which user more older version of hibernate) I used org.hibernate.bytecode.internal.javassist.FieldHandled and org.hibernate.bytecode.internal.javassist.FieldHandler but these classes not exist in hibernate 5.1.10
Can you advice please how to switch on lazy-loading mode ?
Thanks,
Arkady.


Lazy loading works except for the parent side of a @OneToOne association. This is because Hibernate has no other way of knowing whether to assign a null or a Proxy to this variable. More details you can find in this article21.
Now, to fix it, you have two options:
  1. You can activate lazy loading bytecode enhancement 23
  2. Or, you can just remove the parent side and use the client side with @MapsId as explained in this article 21. This way, you will find that you don’t really need the parent side since the child shares the same id with the parent so you can easily fetch the child by knowing the parent id.



--------------------------------------------
   hibernateVersion = '5.2.16.Final' 
    dependencies {
        classpath("org.hibernate:hibernate-gradle-plugin:${hibernateVersion}") 
    }
apply plugin: 'org.hibernate.orm'

hibernate {
    enhance {
        enableLazyInitialization = true
        enableDirtyTracking = false
        enableAssociationManagement = false
        enableExtendedEnhancement = true
    }
}

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "passportId")
    public User getUser() {
        return user;
}

@Exclude
    @OneToOne(mappedBy = "tpuser", fetch = FetchType.LAZY)
    @Basic(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)  //此注解
    public Parent getParent() {
        return parent;
    }

````