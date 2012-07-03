package org.sonatype.m2e.cxf.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.m2e.core.project.ResolverConfiguration;
import org.eclipse.m2e.tests.common.AbstractMavenProjectTestCase;
import org.eclipse.m2e.tests.common.ClasspathHelpers;

@SuppressWarnings( "restriction" )
public class CxfCodegenTest
    extends AbstractMavenProjectTestCase
{
    public void test_p001_simple()
        throws Exception
    {
        ResolverConfiguration configuration = new ResolverConfiguration();
        IProject project1 = importProject( "projects/cxf/cxf-p001/pom.xml", configuration );
        waitForJobsToComplete();

        project1.build( IncrementalProjectBuilder.FULL_BUILD, monitor );
        project1.build( IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor );
        waitForJobsToComplete();

        assertNoErrors( project1 );

        IJavaProject javaProject1 = JavaCore.create( project1 );
        IClasspathEntry[] cp1 = javaProject1.getRawClasspath();

        ClasspathHelpers.assertClasspath( new String[] { "/cxf-p001/src/main/java", //
            "/cxf-p001/src/test/java", //
            "org.eclipse.jdt.launching.JRE_CONTAINER/.*", //
            "org.eclipse.m2e.MAVEN2_CLASSPATH_CONTAINER", //
            "/cxf-p001/target/generated-sources/cxf" //
        }, cp1 );

        assertTrue( project1.getFile( "target/generated-sources/cxf/org/apache/hello_world_soap_http/Greeter.java" ).isSynchronized( IResource.DEPTH_ZERO ) );
        assertTrue( project1.getFile( "target/generated-sources/cxf/org/apache/hello_world_soap_http/Greeter.java" ).isAccessible() );
        assertTrue( project1.getFile( "target/generated-sources/cxf/org/apache/hello_world_soap_http/SOAPService.java" ).isSynchronized( IResource.DEPTH_ZERO ) );
        assertTrue( project1.getFile( "target/generated-sources/cxf/org/apache/hello_world_soap_http/SOAPService.java" ).isAccessible() );
        
        assertTrue( project1.getFile( "target/generated-sources/cxf/org/apache/hello_world_soap_http/types/ObjectFactory.java" ).isSynchronized( IResource.DEPTH_ZERO ) );
        assertTrue( project1.getFile( "target/generated-sources/cxf/org/apache/hello_world_soap_http/types/ObjectFactory.java" ).isAccessible() );
    }

}
