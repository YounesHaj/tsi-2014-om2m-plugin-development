package org.eclipse.om2m.openhab.service;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.SclService;
import org.eclipse.om2m.ipu.service.IpuService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
 
 
 
public class Activator implements BundleActivator {
 
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
 
	 /** Logger */
    private static Log logger = LogFactory.getLog(Activator.class);
    /** SCL service tracker */
    private ServiceTracker<Object, Object> sclServiceTracker;
 
	public void start(BundleContext  context) throws Exception {
 
        logger.info("Register IpuService..");
        context.registerService(IpuService.class.getName(), new IpuController(), null);
        logger.info("IpuService is registered.");
 
        sclServiceTracker = new ServiceTracker<Object, Object>(context, SclService.class.getName(), null) {
            public void removedService(ServiceReference<Object> reference, Object service) {
                logger.info("SclService removed");
            }
 
            public Object addingService(ServiceReference<Object> reference) {
                logger.info("SclService discovered");
                SclService sclService = (SclService) this.context.getService(reference);
                final IpuMonitor IpuMonitor = new IpuMonitor(sclService);
                new Thread(){
                    public void run(){
                        try {
                        	ItemService serv=new ItemService();
                            IpuMonitor.start(serv.GiveMeItems());
                        } catch (Exception e) {
                            logger.error("IpuMonitor error", e);
                        }
                    }
                }.start();
                return sclService;
            }
        };
        sclServiceTracker.open();
    }
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("End Ipu sample");		
	}
 
 
}